package com.sms.backend.repository;

import com.sms.backend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudent_Id(Long studentId);

    List<Attendance> findByStudent_FirstNameContainingIgnoreCase(String keyword);

    boolean existsByStudentCourse_Course_IdAndAttendanceDate(
            Long courseId,
            LocalDate attendanceDate);
}