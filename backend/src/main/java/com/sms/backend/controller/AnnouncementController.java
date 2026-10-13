package com.sms.backend.controller;

import com.sms.backend.dto.request.AnnouncementRequest;
import com.sms.backend.dto.response.AnnouncementResponse;
import com.sms.backend.service.AnnouncementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/v1/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public AnnouncementResponse createAnnouncement(
            @Valid @RequestBody AnnouncementRequest request) {

        return announcementService.createAnnouncement(request);
    }

    @GetMapping("/{id}")
    public AnnouncementResponse getAnnouncementById(
            @PathVariable Long id) {

        return announcementService.getAnnouncementById(id);
    }

    @GetMapping
    public List<AnnouncementResponse> getAllAnnouncements() {

        return announcementService.getAllAnnouncements();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public AnnouncementResponse updateAnnouncement(
            @PathVariable Long id,
            @Valid @RequestBody AnnouncementRequest request) {

        return announcementService.updateAnnouncement(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteAnnouncement(
            @PathVariable Long id) {

        announcementService.deleteAnnouncement(id);

        return "Announcement deleted successfully.";
    }

}