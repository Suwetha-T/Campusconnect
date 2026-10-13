package com.sms.backend.service;

import com.sms.backend.dto.request.CourseRequest;
import com.sms.backend.dto.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(CourseRequest request);

    CourseResponse getCourseById(Long id);

    List<CourseResponse> getAllCourses();

    CourseResponse updateCourse(Long id, CourseRequest request);

    void deleteCourse(Long id);

    List<CourseResponse> searchCourses(String keyword);

    Page<CourseResponse> getCoursesPage(Pageable pageable);

    List<CourseResponse> getFacultyAssignedCourses();
}