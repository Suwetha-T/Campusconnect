package com.sms.backend.service;

import com.sms.backend.dto.request.DepartmentRequest;
import com.sms.backend.dto.response.DepartmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    DepartmentResponse getDepartmentById(Long id);

    List<DepartmentResponse> getAllDepartments();

    Page<DepartmentResponse> getDepartments(Pageable pageable);

    DepartmentResponse updateDepartment(Long id,
                                        DepartmentRequest request);

    void deleteDepartment(Long id);

    List<DepartmentResponse> searchDepartments(String keyword);
}