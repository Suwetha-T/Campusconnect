package com.sms.backend.controller;

import com.sms.backend.dto.request.StudentCourseRequest;
import com.sms.backend.dto.response.StudentCourseResponse;
import com.sms.backend.service.StudentCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student-courses")
@RequiredArgsConstructor
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentCourseResponse createStudentCourse(
            @Valid @RequestBody StudentCourseRequest request) {

        return studentCourseService.createStudentCourse(request);
    }

    @GetMapping("/me")
    public List<StudentCourseResponse> getCurrentStudentCourses() {
        return studentCourseService.getCurrentStudentCourses();
    }


    @GetMapping("/{id}")
    public StudentCourseResponse getStudentCourseById(
            @PathVariable Long id) {

        return studentCourseService.getStudentCourseById(id);
    }

    @GetMapping
    public List<StudentCourseResponse> getAllStudentCourses() {

        return studentCourseService.getAllStudentCourses();
    }

    @GetMapping("/student/{studentId}")
    public List<StudentCourseResponse> getStudentCoursesByStudentId(
            @PathVariable Long studentId) {

        return studentCourseService.getStudentCoursesByStudentId(studentId);
    }

    @PutMapping("/{id}")
    public StudentCourseResponse updateStudentCourse(
            @PathVariable Long id,
            @Valid @RequestBody StudentCourseRequest request) {

        return studentCourseService.updateStudentCourse(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudentCourse(
            @PathVariable Long id) {

        studentCourseService.deleteStudentCourse(id);
    }
}