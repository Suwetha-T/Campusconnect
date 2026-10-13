package com.sms.backend.repository;

import com.sms.backend.entity.Announcement;
import com.sms.backend.enums.AnnouncementTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findTop5ByOrderByCreatedAtDesc();

    List<Announcement> findTop5ByTargetInOrderByCreatedAtDesc(
            Collection<AnnouncementTarget> targets);
}