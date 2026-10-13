package com.sms.backend.mapper;

import com.sms.backend.dto.request.StudentCourseRequest;
import com.sms.backend.dto.response.StudentCourseResponse;
import com.sms.backend.entity.Course;
import com.sms.backend.entity.Student;
import com.sms.backend.entity.StudentCourse;

public class StudentCourseMapper {

    private StudentCourseMapper() {
    }

    public static StudentCourse toEntity(StudentCourseRequest request,
                                         Student student,
                                         Course course) {

        return StudentCourse.builder()
                .student(student)
                .course(course)
                .enrollmentDate(request.getEnrollmentDate())
                .enrollmentStatus(request.getEnrollmentStatus())
                .build();
    }

    public static StudentCourseResponse toResponse(StudentCourse studentCourse) {

        StudentCourseResponse response = new StudentCourseResponse();

        response.setId(studentCourse.getId());

        response.setStudentId(studentCourse.getStudent().getId());
        response.setStudentName(
                studentCourse.getStudent().getFirstName() + " "
                        + studentCourse.getStudent().getLastName()
        );

        response.setCourseId(studentCourse.getCourse().getId());
        response.setCourseName(studentCourse.getCourse().getName());

        response.setEnrollmentDate(studentCourse.getEnrollmentDate());
        response.setEnrollmentStatus(studentCourse.getEnrollmentStatus());

        // Added these two lines
        response.setCreatedAt(studentCourse.getCreatedAt());
        response.setUpdatedAt(studentCourse.getUpdatedAt());

        return response;
    }
}