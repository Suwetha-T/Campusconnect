package com.sms.backend.mapper;

import com.sms.backend.dto.request.AttendanceRequest;
import com.sms.backend.dto.response.AttendanceResponse;
import com.sms.backend.entity.Attendance;
import com.sms.backend.entity.StudentCourse;

public class AttendanceMapper {

    private AttendanceMapper() {
    }

    public static Attendance toEntity(AttendanceRequest request,
                                      StudentCourse studentCourse) {

        return Attendance.builder()
                .student(studentCourse.getStudent())
                .studentCourse(studentCourse)
                .attendanceDate(request.getAttendanceDate())
                .attendanceStatus(request.getAttendanceStatus())
                .build();
    }

    public static AttendanceResponse toResponse(Attendance attendance) {

        AttendanceResponse response = new AttendanceResponse();

        response.setId(attendance.getId());

        // Student Course
        response.setStudentCourseId(attendance.getStudentCourse().getId());

        // Student
        response.setStudentId(attendance.getStudent().getId());
        response.setStudentName(
                attendance.getStudent().getFirstName() + " "
                        + attendance.getStudent().getLastName()
        );

        // Course
        response.setCourseId(attendance.getStudentCourse().getCourse().getId());
        response.setCourseName(
                attendance.getStudentCourse().getCourse().getName()
        );

        // Attendance Details
        response.setAttendanceDate(attendance.getAttendanceDate());
        response.setAttendanceStatus(attendance.getAttendanceStatus());

        // Audit Fields
        response.setCreatedAt(attendance.getCreatedAt());
        response.setUpdatedAt(attendance.getUpdatedAt());

        return response;
    }
}