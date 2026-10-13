package com.sms.backend.controller;

import com.sms.backend.dto.request.DepartmentRequest;
import com.sms.backend.dto.response.DepartmentResponse;
import com.sms.backend.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        return departmentService.createDepartment(request);
    }

    @GetMapping("/{id}")
    public DepartmentResponse getDepartmentById(
            @PathVariable Long id) {

        return departmentService.getDepartmentById(id);
    }

    @GetMapping
    public List<DepartmentResponse> getAllDepartments() {

        return departmentService.getAllDepartments();
    }

    @GetMapping("/page")
    public Page<DepartmentResponse> getDepartments(Pageable pageable) {

        return departmentService.getDepartments(pageable);
    }

    @GetMapping("/search")
    public List<DepartmentResponse> searchDepartments(
            @RequestParam String keyword) {

        return departmentService.searchDepartments(keyword);
    }

    @PutMapping("/{id}")
    public DepartmentResponse updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request) {

        return departmentService.updateDepartment(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(
            @PathVariable Long id) {

        departmentService.deleteDepartment(id);
    }
}