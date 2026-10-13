package com.sms.backend.repository;

import com.sms.backend.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {

    List<StudentCourse> findByStudent_Id(Long studentId);
    
    java.util.Optional<StudentCourse> findByStudent_IdAndCourse_Id(Long studentId, Long courseId);
}