package com.sms.backend.mapper;

import com.sms.backend.dto.request.StudentRequest;
import com.sms.backend.dto.response.StudentResponse;
import com.sms.backend.entity.Department;
import com.sms.backend.entity.Student;
import com.sms.backend.entity.User;

public class StudentMapper {

    private StudentMapper() {
    }

    public static Student toEntity(
            StudentRequest request,
            Department department,
            User user) {

        return Student.builder()
                .user(user)
                .department(department)
                .enrollmentNumber(request.getEnrollmentNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .profilePhoto(request.getProfilePhoto())
                .semester(request.getSemester())
                .academicYear(request.getAcademicYear())
                .build();
    }

    public static StudentResponse toResponse(Student student) {

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());

        response.setUserId(student.getUser().getId());
        response.setUserEmail(student.getUser().getEmail());

        response.setDepartmentId(student.getDepartment().getId());
        response.setDepartmentName(student.getDepartment().getName());

        response.setEnrollmentNumber(student.getEnrollmentNumber());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setPhone(student.getPhone());
        response.setGender(student.getGender());
        response.setDateOfBirth(student.getDateOfBirth());
        response.setAddress(student.getAddress());
        response.setProfilePhoto(student.getProfilePhoto());
        response.setSemester(student.getSemester());
        response.setAcademicYear(student.getAcademicYear());

        response.setCreatedAt(student.getCreatedAt());
        response.setUpdatedAt(student.getUpdatedAt());

        return response;
    }
}