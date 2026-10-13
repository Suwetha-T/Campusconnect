package com.sms.backend.dto.request;

import com.sms.backend.enums.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCourseRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotNull(message = "Enrollment date is required")
    private LocalDate enrollmentDate;

    @NotNull(message = "Enrollment status is required")
    private EnrollmentStatus enrollmentStatus;
}