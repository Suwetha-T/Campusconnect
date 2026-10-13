package com.sms.backend.dto.response;

import com.sms.backend.enums.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceResponse {

    private Long id;

    private Long studentCourseId;

    private Long studentId;
    private String studentName;

    private Long courseId;
    private String courseName;

    private LocalDate attendanceDate;

    private AttendanceStatus attendanceStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}