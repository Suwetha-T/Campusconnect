package com.sms.backend.controller;

import com.sms.backend.dto.request.CourseRequest;
import com.sms.backend.dto.response.CourseResponse;
import com.sms.backend.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public CourseResponse createCourse(
            @Valid @RequestBody CourseRequest request) {

        return courseService.createCourse(request);

    }

    @GetMapping("/me")
    public List<CourseResponse> getFacultyAssignedCourses() {
        return courseService.getFacultyAssignedCourses();
    }


    @GetMapping("/{id}")
    public CourseResponse getCourseById(
            @PathVariable Long id) {

        return courseService.getCourseById(id);

    }

    @GetMapping
    public List<CourseResponse> getAllCourses() {

        return courseService.getAllCourses();

    }

    @GetMapping("/search")
    public List<CourseResponse> searchCourses(
            @RequestParam String keyword) {

        return courseService.searchCourses(keyword);

    }

    @GetMapping("/page")
    public Page<CourseResponse> getCoursesPage(
            Pageable pageable) {

        return courseService.getCoursesPage(pageable);

    }

    @PutMapping("/{id}")
    public CourseResponse updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest request) {

        return courseService.updateCourse(id, request);

    }

    @DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteCourse(
        @PathVariable Long id) {

    courseService.deleteCourse(id);

}

}