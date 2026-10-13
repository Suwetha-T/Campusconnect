package com.sms.backend.service.impl;

import com.sms.backend.dto.request.DepartmentRequest;
import com.sms.backend.dto.response.DepartmentResponse;
import com.sms.backend.entity.Department;
import com.sms.backend.exception.DuplicateResourceException;
import com.sms.backend.exception.ResourceNotFoundException;
import com.sms.backend.mapper.DepartmentMapper;
import com.sms.backend.repository.DepartmentRepository;
import com.sms.backend.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        if (departmentRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Department code already exists");
        }

        if (departmentRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Department name already exists");
        }

        Department department = departmentMapper.toEntity(request);

        Department savedDepartment = departmentRepository.save(department);

        return departmentMapper.toResponse(savedDepartment);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        return departmentMapper.toResponse(department);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DepartmentResponse> getDepartments(Pageable pageable) {

        return departmentRepository
                .findAll(pageable)
                .map(departmentMapper::toResponse);
    }

    @Override
    public DepartmentResponse updateDepartment(Long id,
                                               DepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        department.setName(request.getName());
        department.setCode(request.getCode());
        department.setDescription(request.getDescription());

        Department updatedDepartment = departmentRepository.save(department);

        return departmentMapper.toResponse(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        departmentRepository.delete(department);
    }

    @Override
    public List<DepartmentResponse> searchDepartments(String keyword) {

        return departmentRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(departmentMapper::toResponse)
                .collect(Collectors.toList());
    }
}