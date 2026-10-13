package com.sms.backend.dto.request;

import com.sms.backend.enums.ExamType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarksRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotNull(message = "Exam type is required")
    private ExamType examType;

    @NotNull(message = "Marks obtained is required")
    @jakarta.validation.constraints.Min(value = 0, message = "Marks obtained cannot be negative")
    private Double marksObtained;

    @NotNull(message = "Maximum marks is required")
    @jakarta.validation.constraints.Min(value = 0, message = "Maximum marks cannot be negative")
    private Double maximumMarks;

    @jakarta.validation.constraints.AssertTrue(message = "Marks obtained cannot be greater than maximum marks")
    public boolean isValidMarks() {
        if (marksObtained == null || maximumMarks == null) {
            return true;
        }
        return marksObtained <= maximumMarks;
    }
}