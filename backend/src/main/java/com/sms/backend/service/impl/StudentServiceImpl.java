package com.sms.backend.service.impl;

import com.sms.backend.dto.request.StudentRequest;
import com.sms.backend.dto.response.StudentResponse;
import com.sms.backend.entity.Department;
import com.sms.backend.entity.Student;
import com.sms.backend.entity.User;
import com.sms.backend.exception.DuplicateResourceException;
import com.sms.backend.exception.ResourceNotFoundException;
import com.sms.backend.mapper.StudentMapper;
import com.sms.backend.repository.DepartmentRepository;
import com.sms.backend.repository.StudentRepository;
import com.sms.backend.repository.UserRepository;
import com.sms.backend.service.StudentService;
import com.sms.backend.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    @Override
    public StudentResponse createStudent(StudentRequest request) {

        if (studentRepository.existsByEnrollmentNumber(request.getEnrollmentNumber())) {
            throw new DuplicateResourceException(
                    "Enrollment number already exists");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .or(() -> departmentRepository.findByCode(String.valueOf(request.getDepartmentId())))
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Student student = StudentMapper.toEntity(
                request,
                department,
                user
        );

        Student savedStudent = studentRepository.save(student);

        return StudentMapper.toResponse(savedStudent);
    }

    @Override
    public StudentResponse getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));

        return StudentMapper.toResponse(student);
    }

    @Override
    public List<StudentResponse> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toResponse)
                .toList();
    }

    @Override
    public StudentResponse updateStudent(Long id,
                                         StudentRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .or(() -> departmentRepository.findByCode(String.valueOf(request.getDepartmentId())))
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        student.setUser(user);
        student.setDepartment(department);
        student.setEnrollmentNumber(request.getEnrollmentNumber());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setPhone(request.getPhone());
        student.setGender(request.getGender());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setAddress(request.getAddress());
        student.setProfilePhoto(request.getProfilePhoto());
        student.setSemester(request.getSemester());
        student.setAcademicYear(request.getAcademicYear());

        Student updatedStudent = studentRepository.save(student);

        return StudentMapper.toResponse(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));

        studentRepository.delete(student);
    }

    @Override
    public List<StudentResponse> searchStudents(String keyword) {

        return studentRepository
                .findByFirstNameContainingIgnoreCase(keyword)
                .stream()
                .map(StudentMapper::toResponse)
                .toList();
    }

    @Override
    public Page<StudentResponse> getStudentsPage(Pageable pageable) {

        return studentRepository
                .findAll(pageable)
                .map(StudentMapper::toResponse);
    }

    @Override
    public StudentResponse getCurrentStudentProfile() {

        String currentUserEmail = SecurityUtils.getCurrentUserEmail();

        Student student = studentRepository
                .findByUser_Email(currentUserEmail)
                .orElseGet(() -> {
                    User user = userRepository.findByEmail(currentUserEmail)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found for student profile."));
                    return createMissingStudentProfile(user);
                });

        return StudentMapper.toResponse(student);
    }

    private Student createMissingStudentProfile(User user) {
        Department department = departmentRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Department not found."));

        String localPart = user.getEmail() != null ? user.getEmail().split("@")[0] : "student";
        String firstName = localPart.contains(".") ? localPart.split("\\.")[0] : localPart;
        String lastName = localPart.contains(".") ? localPart.split("\\.")[1] : "Student";
        String enrollmentNumber = generateUniqueEnrollmentNumber(user.getId());

        String normalizedFirstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        String normalizedLastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

        Student student = Student.builder()
                .user(user)
                .department(department)
                .enrollmentNumber(enrollmentNumber)
                .firstName(normalizedFirstName)
                .lastName(normalizedLastName)
                .semester(1)
                .academicYear("2024-2025")
                .build();

        return studentRepository.save(student);
    }

    private String generateUniqueEnrollmentNumber(Long userId) {
        String base = "STU" + userId;
        String candidate = base;
        int suffix = 1;
        while (studentRepository.existsByEnrollmentNumber(candidate)) {
            candidate = base + "-" + suffix;
            suffix++;
        }
        return candidate;
    }

}