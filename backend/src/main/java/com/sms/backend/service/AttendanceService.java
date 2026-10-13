package com.sms.backend.service;

import com.sms.backend.dto.request.AttendanceRequest;
import com.sms.backend.dto.response.AttendanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AttendanceService {

    AttendanceResponse createAttendance(AttendanceRequest request);

    AttendanceResponse getAttendanceById(Long id);

    List<AttendanceResponse> getAllAttendance();

    AttendanceResponse updateAttendance(Long id, AttendanceRequest request);

    void deleteAttendance(Long id);

    List<AttendanceResponse> searchAttendance(String keyword);

    Page<AttendanceResponse> getAttendancePage(Pageable pageable);

    List<AttendanceResponse> getCurrentStudentAttendance();
}