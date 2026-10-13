package com.sms.backend.dto.response;

import com.sms.backend.enums.ExamType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarksResponse {

    private Long id;

    private Long studentCourseId;

    private Long studentId;
    private String studentName;

    private Long courseId;
    private String courseName;

    private ExamType examType;

    private Double marksObtained;

    private Double maximumMarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}