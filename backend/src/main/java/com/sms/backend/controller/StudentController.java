package com.sms.backend.controller;

import com.sms.backend.dto.request.StudentRequest;
import com.sms.backend.dto.response.StudentResponse;
import com.sms.backend.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public StudentResponse createStudent(
            @Valid @RequestBody StudentRequest request) {

        return studentService.createStudent(request);
    }

    @GetMapping("/me")
    public StudentResponse getCurrentStudentProfile() {
        return studentService.getCurrentStudentProfile();
    }


    @GetMapping("/{id}")
    public StudentResponse getStudentById(
            @PathVariable Long id) {

        return studentService.getStudentById(id);
    }

    @GetMapping
    public List<StudentResponse> getAllStudents() {

        return studentService.getAllStudents();
    }

    @GetMapping("/search")
    public List<StudentResponse> searchStudents(
            @RequestParam String keyword) {

        return studentService.searchStudents(keyword);
    }

    @GetMapping("/page")
    public Page<StudentResponse> getStudentsPage(
            Pageable pageable) {

        return studentService.getStudentsPage(pageable);
    }

    @PutMapping("/{id}")
    public StudentResponse updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {

        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(
            @PathVariable Long id) {

        studentService.deleteStudent(id);

        return "Student deleted successfully.";
    }

}