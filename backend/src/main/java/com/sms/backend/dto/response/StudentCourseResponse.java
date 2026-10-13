package com.sms.backend.dto.response;

import com.sms.backend.enums.EnrollmentStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCourseResponse {

    private Long id;

    private Long studentId;
    private String studentName;

    private Long courseId;
    private String courseName;

    private LocalDate enrollmentDate;

    private EnrollmentStatus enrollmentStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}