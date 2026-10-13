package com.sms.backend.dto.request;

import com.sms.backend.enums.AnnouncementTarget;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Target is required")
    private AnnouncementTarget target;

    @NotNull(message = "Created By User ID is required")
    private Long createdById;
}