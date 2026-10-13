package com.sms.backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponse {

    private Long id;

    private String name;

    private String code;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}