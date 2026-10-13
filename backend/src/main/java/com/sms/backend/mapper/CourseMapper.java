package com.sms.backend.mapper;

import com.sms.backend.dto.request.CourseRequest;
import com.sms.backend.dto.response.CourseResponse;
import com.sms.backend.entity.Course;
import com.sms.backend.entity.Department;
import com.sms.backend.entity.Faculty;

public class CourseMapper {

    private CourseMapper() {
    }

    public static Course toEntity(CourseRequest request,
                                  Department department,
                                  Faculty faculty) {

        return Course.builder()
                .department(department)
                .faculty(faculty)
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .credits(request.getCredits())
                .semester(request.getSemester())
                .build();
    }

    public static CourseResponse toResponse(Course course) {

        CourseResponse response = new CourseResponse();

        response.setId(course.getId());

        response.setDepartmentId(course.getDepartment().getId());
        response.setDepartmentName(course.getDepartment().getName());

        response.setFacultyId(course.getFaculty().getId());
        response.setFacultyName(
                course.getFaculty().getFirstName() + " "
                        + course.getFaculty().getLastName()
        );

        response.setCode(course.getCode());
        response.setName(course.getName());
        response.setDescription(course.getDescription());
        response.setCredits(course.getCredits());
        response.setSemester(course.getSemester());

        // Added these two lines
        response.setCreatedAt(course.getCreatedAt());
        response.setUpdatedAt(course.getUpdatedAt());

        return response;
    }
}