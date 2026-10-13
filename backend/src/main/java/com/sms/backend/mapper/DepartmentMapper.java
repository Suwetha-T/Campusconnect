package com.sms.backend.mapper;

import com.sms.backend.dto.request.DepartmentRequest;
import com.sms.backend.dto.response.DepartmentResponse;
import com.sms.backend.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentRequest request) {

        if (request == null) {
            return null;
        }

        return Department.builder()
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .build();
    }

    public DepartmentResponse toResponse(Department department) {

        if (department == null) {
            return null;
        }

        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .description(department.getDescription())
                .createdAt(department.getCreatedAt())
                .updatedAt(department.getUpdatedAt())
                .build();
    }
}