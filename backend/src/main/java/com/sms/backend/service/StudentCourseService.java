package com.sms.backend.service;

import com.sms.backend.dto.request.StudentCourseRequest;
import com.sms.backend.dto.response.StudentCourseResponse;

import java.util.List;

public interface StudentCourseService {

    StudentCourseResponse createStudentCourse(StudentCourseRequest request);

    StudentCourseResponse getStudentCourseById(Long id);

    List<StudentCourseResponse> getAllStudentCourses();

    StudentCourseResponse updateStudentCourse(Long id, StudentCourseRequest request);

    void deleteStudentCourse(Long id);

    List<StudentCourseResponse> getCurrentStudentCourses();

    List<StudentCourseResponse> getStudentCoursesByStudentId(Long studentId);
}