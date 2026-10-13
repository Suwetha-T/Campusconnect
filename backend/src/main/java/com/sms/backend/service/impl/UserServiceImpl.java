package com.sms.backend.service.impl;

import com.sms.backend.dto.request.LoginRequest;
import com.sms.backend.dto.request.RegisterRequest;
import com.sms.backend.dto.request.UserRequest;
import com.sms.backend.dto.response.AuthResponse;
import com.sms.backend.dto.response.UserResponse;
import com.sms.backend.entity.Department;
import com.sms.backend.entity.Faculty;
import com.sms.backend.entity.Student;
import com.sms.backend.entity.User;
import com.sms.backend.enums.Gender;
import com.sms.backend.enums.Role;
import com.sms.backend.exception.BadRequestException;
import com.sms.backend.exception.DuplicateResourceException;
import com.sms.backend.exception.ResourceNotFoundException;
import com.sms.backend.mapper.UserMapper;
import com.sms.backend.repository.DepartmentRepository;
import com.sms.backend.repository.FacultyRepository;
import com.sms.backend.repository.StudentRepository;
import com.sms.backend.repository.UserRepository;
import com.sms.backend.security.jwt.JwtService;
import com.sms.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = UserMapper.toEntity(
                request,
                passwordEncoder.encode(request.getPassword())
        );

        User savedUser = userRepository.save(user);

        // Auto-create profile to avoid "Profile not found" errors on login
        if (savedUser.getRole() == Role.FACULTY) {
            createDefaultFacultyProfile(savedUser);
        } else if (savedUser.getRole() == Role.STUDENT) {
            createDefaultStudentProfile(savedUser);
        }

        String token = jwtService.generateToken(savedUser.getEmail());

        return AuthResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .token(token)
                .message("Registration Successful")
                .build();
    }

    @Override
    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        // Auto-create profile so student/faculty appears in their respective lists
        if (savedUser.getRole() == Role.FACULTY) {
            createDefaultFacultyProfile(savedUser);
        } else if (savedUser.getRole() == Role.STUDENT) {
            createDefaultStudentProfile(savedUser);
        }

        return toUserResponse(savedUser);
    }

    @Override
    public Page<UserResponse> getUsers(Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(this::toUserResponse);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updatedUser = userRepository.save(user);

        return toUserResponse(updatedUser);
    }

    @Override
    public void toggleUserStatus(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setIsActive(!user.getIsActive());

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);
    }

    @Override
    public List<UserResponse> searchUsers(String keyword) {

        return userRepository.findByEmailContainingIgnoreCase(keyword)
                .stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BadRequestException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new BadRequestException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .token(token)
                .message("Login Successful")
                .build();
    }

    // ─── Private Helpers ──────────────────────────────────────────────────────

    private UserResponse toUserResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .build();
    }

    private void createDefaultFacultyProfile(User user) {
        Department dept = getDefaultDepartment();
        Faculty faculty = Faculty.builder()
                .user(user)
                .department(dept)
                .firstName("New")
                .lastName("Faculty")
                .employeeCode("EMP-" + System.currentTimeMillis())
                .gender(Gender.MALE)
                .phone("0000000000")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .address("Update address")
                .build();
        facultyRepository.save(faculty);
    }

    private void createDefaultStudentProfile(User user) {
        Department dept = getDefaultDepartment();
        Student student = Student.builder()
                .user(user)
                .department(dept)
                .firstName("New")
                .lastName("Student")
                .enrollmentNumber("STU-" + System.currentTimeMillis())
                .gender(Gender.MALE)
                .phone("0000000000")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("Update address")
                .academicYear("2023-2024")
                .semester(1)
                .build();
        studentRepository.save(student);
    }

    private Department getDefaultDepartment() {
        List<Department> departments = departmentRepository.findAll();
        if (!departments.isEmpty()) {
            return departments.get(0);
        }
        Department dept = Department.builder()
                .name("General")
                .code("GEN")
                .description("General Department")
                .build();
        return departmentRepository.save(dept);
    }
}