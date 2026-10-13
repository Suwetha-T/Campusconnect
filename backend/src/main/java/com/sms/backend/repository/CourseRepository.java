package com.sms.backend.repository;

import com.sms.backend.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCode(String code);

    boolean existsByCode(String code);

    List<Course> findByNameContainingIgnoreCase(String keyword);

    Page<Course> findByNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    List<Course> findByFaculty_Id(Long facultyId);

}