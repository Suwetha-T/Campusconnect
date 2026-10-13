package com.sms.backend.service;

import com.sms.backend.dto.request.AnnouncementRequest;
import com.sms.backend.dto.response.AnnouncementResponse;

import java.util.List;

public interface AnnouncementService {

    AnnouncementResponse createAnnouncement(AnnouncementRequest request);

    AnnouncementResponse getAnnouncementById(Long id);

    List<AnnouncementResponse> getAllAnnouncements();

    AnnouncementResponse updateAnnouncement(Long id,
                                            AnnouncementRequest request);

    void deleteAnnouncement(Long id);

}