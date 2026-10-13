package com.sms.backend.repository;

import com.sms.backend.entity.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Optional<Faculty> findByUser_Email(String email);

    Optional<Faculty> findByEmployeeCode(String employeeCode);

    boolean existsByEmployeeCode(String employeeCode);

    List<Faculty> findByFirstNameContainingIgnoreCase(String keyword);

    Page<Faculty> findByFirstNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

}