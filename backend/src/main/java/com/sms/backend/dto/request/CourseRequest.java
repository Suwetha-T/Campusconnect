package com.sms.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequest {

    @NotNull
    private Long departmentId;

    @NotNull
    private Long facultyId;

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Integer credits;

    @NotNull
    private Integer semester;
}