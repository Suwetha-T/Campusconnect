package com.sms.backend.repository;

import com.sms.backend.entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Long> {

    List<Marks> findByStudent_Id(Long studentId);

    List<Marks> findTop5ByStudent_IdOrderByCreatedAtDesc(Long studentId);

    List<Marks> findByStudent_FirstNameContainingIgnoreCase(String keyword);

}