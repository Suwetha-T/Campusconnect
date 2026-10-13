package com.sms.backend.service.impl;

import com.sms.backend.dto.request.FacultyRequest;
import com.sms.backend.dto.response.FacultyResponse;
import com.sms.backend.entity.Department;
import com.sms.backend.entity.Faculty;
import com.sms.backend.entity.User;
import com.sms.backend.exception.DuplicateResourceException;
import com.sms.backend.exception.ResourceNotFoundException;
import com.sms.backend.mapper.FacultyMapper;
import com.sms.backend.repository.DepartmentRepository;
import com.sms.backend.repository.FacultyRepository;
import com.sms.backend.repository.UserRepository;
import com.sms.backend.service.FacultyService;
import com.sms.backend.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    @Override
    public FacultyResponse getCurrentFacultyProfile() {
        String currentUserEmail = SecurityUtils.getCurrentUserEmail();

        Faculty faculty = facultyRepository
                .findByUser_Email(currentUserEmail)
                .orElseGet(() -> {
                    User user = userRepository.findByEmail(currentUserEmail)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found for faculty profile."));
                    return createMissingFacultyProfile(user);
                });

        return FacultyMapper.toResponse(faculty);
    }

    private Faculty createMissingFacultyProfile(User user) {
        Department department = departmentRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Department not found."));

        String localPart = user.getEmail() != null ? user.getEmail().split("@")[0] : "faculty";
        String firstName = localPart.contains(".") ? localPart.split("\\.")[0] : localPart;
        String lastName = localPart.contains(".") ? localPart.split("\\.")[1] : "Faculty";

        Faculty faculty = Faculty.builder()
                .user(user)
                .department(department)
                .employeeCode(generateUniqueEmployeeCode(user.getId()))
                .firstName(firstName.substring(0, 1).toUpperCase() + firstName.substring(1))
                .lastName(lastName.substring(0, 1).toUpperCase() + lastName.substring(1))
                .build();

        return facultyRepository.save(faculty);
    }

    private String generateUniqueEmployeeCode(Long userId) {
        String base = "EMP" + userId;
        String candidate = base;
        int suffix = 1;
        while (facultyRepository.existsByEmployeeCode(candidate)) {
            candidate = base + "-" + suffix;
            suffix++;
        }
        return candidate;
    }

    @Override
public FacultyResponse createFaculty(FacultyRequest request) {

    if (facultyRepository.existsByEmployeeCode(request.getEmployeeCode())) {
        throw new DuplicateResourceException("Employee code already exists.");
    }

    Department department = departmentRepository.findById(request.getDepartmentId())
            .or(() -> departmentRepository.findByCode(String.valueOf(request.getDepartmentId())))
            .orElseThrow(() ->
                    new ResourceNotFoundException("Department not found."));

    User user = userRepository.findById(request.getUserId())
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found."));

    Faculty faculty = FacultyMapper.toEntity(request, department, user);

    Faculty savedFaculty = facultyRepository.save(faculty);

    return FacultyMapper.toResponse(savedFaculty);

}

    @Override
    public FacultyResponse getFacultyById(Long id) {

        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Faculty not found."));

        return FacultyMapper.toResponse(faculty);
    }

    @Override
    public List<FacultyResponse> getAllFaculty() {

        return facultyRepository.findAll()
                .stream()
                .map(FacultyMapper::toResponse)
                .toList();

    }

    @Override
public FacultyResponse updateFaculty(Long id,
                                     FacultyRequest request) {

    Faculty faculty = facultyRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Faculty not found."));

    if (!faculty.getEmployeeCode().equals(request.getEmployeeCode())
            && facultyRepository.existsByEmployeeCode(request.getEmployeeCode())) {

        throw new DuplicateResourceException("Employee code already exists.");

    }

    Department department = departmentRepository.findById(request.getDepartmentId())
            .or(() -> departmentRepository.findByCode(String.valueOf(request.getDepartmentId())))
            .orElseThrow(() ->
                    new ResourceNotFoundException("Department not found."));

    User user = userRepository.findById(request.getUserId())
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found."));

    faculty.setDepartment(department);
    faculty.setUser(user);

    faculty.setEmployeeCode(request.getEmployeeCode());
    faculty.setFirstName(request.getFirstName());
    faculty.setLastName(request.getLastName());
    faculty.setPhone(request.getPhone());
    faculty.setGender(request.getGender());
    faculty.setDateOfBirth(request.getDateOfBirth());
    faculty.setAddress(request.getAddress());
    faculty.setProfilePhoto(request.getProfilePhoto());

    Faculty updatedFaculty = facultyRepository.save(faculty);

    return FacultyMapper.toResponse(updatedFaculty);

}

    @Override
    public void deleteFaculty(Long id) {

        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Faculty not found."));

        facultyRepository.delete(faculty);

    }

    @Override
    public List<FacultyResponse> searchFaculty(String keyword) {

        return facultyRepository
                .findByFirstNameContainingIgnoreCase(keyword)
                .stream()
                .map(FacultyMapper::toResponse)
                .toList();

    }

    @Override
    public Page<FacultyResponse> getFacultyPage(Pageable pageable) {

        return facultyRepository
                .findAll(pageable)
                .map(FacultyMapper::toResponse);

    }

}