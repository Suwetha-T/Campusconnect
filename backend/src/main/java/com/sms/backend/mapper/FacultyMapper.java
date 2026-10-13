package com.sms.backend.mapper;

import com.sms.backend.dto.request.FacultyRequest;
import com.sms.backend.dto.response.FacultyResponse;
import com.sms.backend.entity.Department;
import com.sms.backend.entity.Faculty;
import com.sms.backend.entity.User;

public class FacultyMapper {

    private FacultyMapper() {
    }

    public static Faculty toEntity(
            FacultyRequest request,
            Department department,
            User user) {

        return Faculty.builder()
                .user(user)
                .department(department)
                .employeeCode(request.getEmployeeCode())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .profilePhoto(request.getProfilePhoto())
                .build();
    }

    public static FacultyResponse toResponse(Faculty faculty) {

        FacultyResponse response = new FacultyResponse();

        // BaseEntity fields
        response.setId(faculty.getId());
        response.setCreatedAt(faculty.getCreatedAt());
        response.setUpdatedAt(faculty.getUpdatedAt());

        // User details
        response.setUserId(faculty.getUser().getId());
        response.setUserEmail(faculty.getUser().getEmail());

        // Department details
        response.setDepartmentId(faculty.getDepartment().getId());
        response.setDepartmentName(faculty.getDepartment().getName());

        // Faculty details
        response.setEmployeeCode(faculty.getEmployeeCode());
        response.setFirstName(faculty.getFirstName());
        response.setLastName(faculty.getLastName());
        response.setPhone(faculty.getPhone());
        response.setGender(faculty.getGender());
        response.setDateOfBirth(faculty.getDateOfBirth());
        response.setAddress(faculty.getAddress());
        response.setProfilePhoto(faculty.getProfilePhoto());

        return response;
    }
}