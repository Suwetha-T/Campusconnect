package com.sms.backend.mapper;

import com.sms.backend.dto.request.AnnouncementRequest;
import com.sms.backend.dto.response.AnnouncementResponse;
import com.sms.backend.entity.Announcement;
import com.sms.backend.entity.User;

public class AnnouncementMapper {

    private AnnouncementMapper() {
    }

    public static Announcement toEntity(AnnouncementRequest request,
                                        User createdBy) {

        return Announcement.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .target(request.getTarget())
                .createdBy(createdBy)
                .build();
    }

    public static AnnouncementResponse toResponse(Announcement announcement) {

        AnnouncementResponse response = new AnnouncementResponse();

        response.setId(announcement.getId());
        response.setTitle(announcement.getTitle());
        response.setContent(announcement.getContent());
        response.setTarget(announcement.getTarget());

        response.setCreatedById(announcement.getCreatedBy().getId());
        response.setCreatedByEmail(announcement.getCreatedBy().getEmail());

        response.setCreatedAt(announcement.getCreatedAt());
        response.setUpdatedAt(announcement.getUpdatedAt());

        return response;
    }
}