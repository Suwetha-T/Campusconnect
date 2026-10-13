package com.sms.backend.service.impl;

import com.sms.backend.dto.request.AnnouncementRequest;
import com.sms.backend.dto.response.AnnouncementResponse;
import com.sms.backend.entity.Announcement;
import com.sms.backend.entity.User;
import com.sms.backend.exception.ResourceNotFoundException;
import com.sms.backend.mapper.AnnouncementMapper;
import com.sms.backend.repository.AnnouncementRepository;
import com.sms.backend.repository.UserRepository;
import com.sms.backend.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;

    @Override
    public AnnouncementResponse createAnnouncement(
            AnnouncementRequest request) {

        User createdBy = userRepository.findById(request.getCreatedById())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        Announcement announcement =
                AnnouncementMapper.toEntity(request, createdBy);

        Announcement savedAnnouncement =
                announcementRepository.save(announcement);

        return AnnouncementMapper.toResponse(savedAnnouncement);
    }

    @Override
    public AnnouncementResponse getAnnouncementById(Long id) {

        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Announcement not found."));

        return AnnouncementMapper.toResponse(announcement);
    }

    @Override
    public List<AnnouncementResponse> getAllAnnouncements() {

        return announcementRepository.findAll()
                .stream()
                .map(AnnouncementMapper::toResponse)
                .toList();
    }

    @Override
    public AnnouncementResponse updateAnnouncement(
            Long id,
            AnnouncementRequest request) {

        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Announcement not found."));

        User createdBy = userRepository.findById(request.getCreatedById())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setTarget(request.getTarget());
        announcement.setCreatedBy(createdBy);

        Announcement updatedAnnouncement =
                announcementRepository.save(announcement);

        return AnnouncementMapper.toResponse(updatedAnnouncement);
    }

    @Override
    public void deleteAnnouncement(Long id) {

        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Announcement not found."));

        announcementRepository.delete(announcement);
    }
}