package com.sms.backend.mapper;

import com.sms.backend.dto.request.MarksRequest;
import com.sms.backend.dto.response.MarksResponse;
import com.sms.backend.entity.Marks;
import com.sms.backend.entity.StudentCourse;

public class MarksMapper {

    private MarksMapper() {
    }

    public static Marks toEntity(
            MarksRequest request,
            StudentCourse studentCourse) {

        return Marks.builder()
                .student(studentCourse.getStudent())
                .studentCourse(studentCourse)
                .examType(request.getExamType())
                .marksObtained(request.getMarksObtained())
                .maximumMarks(request.getMaximumMarks())
                .build();
    }

    public static MarksResponse toResponse(Marks marks) {

        MarksResponse response = new MarksResponse();

        response.setId(marks.getId());

        response.setStudentCourseId(marks.getStudentCourse().getId());

        response.setStudentId(marks.getStudent().getId());
        response.setStudentName(
                marks.getStudent().getFirstName() + " "
                        + marks.getStudent().getLastName());

        response.setCourseId(
                marks.getStudentCourse().getCourse().getId());
        response.setCourseName(
                marks.getStudentCourse().getCourse().getName());

        response.setExamType(marks.getExamType());
        response.setMarksObtained(marks.getMarksObtained());
        response.setMaximumMarks(marks.getMaximumMarks());

        response.setCreatedAt(marks.getCreatedAt());
        response.setUpdatedAt(marks.getUpdatedAt());

        return response;
    }
}