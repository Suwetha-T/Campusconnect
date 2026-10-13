package com.sms.backend.repository;

import com.sms.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUser_Email(String email);

    Optional<Student> findByEnrollmentNumber(String enrollmentNumber);

    boolean existsByEnrollmentNumber(String enrollmentNumber);

    List<Student> findByFirstNameContainingIgnoreCase(String keyword);

    Page<Student> findByFirstNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );
}