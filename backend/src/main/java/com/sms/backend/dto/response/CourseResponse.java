package com.sms.backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponse {

    private Long id;

    private Long departmentId;

    private String departmentName;

    private Long facultyId;

    private String facultyName;

    private String code;

    private String name;

    private String description;

    private Integer credits;

    private Integer semester;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}