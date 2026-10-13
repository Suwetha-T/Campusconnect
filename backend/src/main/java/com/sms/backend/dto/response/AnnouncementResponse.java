package com.sms.backend.dto.response;

import com.sms.backend.enums.AnnouncementTarget;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementResponse {

    private Long id;

    private String title;

    private String content;

    private AnnouncementTarget target;

    private Long createdById;

    private String createdByEmail;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}