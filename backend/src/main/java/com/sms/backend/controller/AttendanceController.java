package com.sms.backend.controller;

import com.sms.backend.dto.request.AttendanceRequest;
import com.sms.backend.dto.response.AttendanceResponse;
import com.sms.backend.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public AttendanceResponse createAttendance(
            @Valid @RequestBody AttendanceRequest request) {

        return attendanceService.createAttendance(request);

    }

    @GetMapping("/me")
    public List<AttendanceResponse> getCurrentStudentAttendance() {
        return attendanceService.getCurrentStudentAttendance();
    }


    @GetMapping("/{id}")
    public AttendanceResponse getAttendanceById(
            @PathVariable Long id) {

        return attendanceService.getAttendanceById(id);

    }

    @GetMapping
    public List<AttendanceResponse> getAllAttendance() {

        return attendanceService.getAllAttendance();

    }

    @GetMapping("/search")
    public List<AttendanceResponse> searchAttendance(
            @RequestParam String keyword) {

        return attendanceService.searchAttendance(keyword);

    }

    @GetMapping("/page")
    public Page<AttendanceResponse> getAttendancePage(
            Pageable pageable) {

        return attendanceService.getAttendancePage(pageable);

    }

    @PutMapping("/{id}")
    public AttendanceResponse updateAttendance(
            @PathVariable Long id,
            @Valid @RequestBody AttendanceRequest request) {

        return attendanceService.updateAttendance(id, request);

    }

    @DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteAttendance(
        @PathVariable Long id) {

    attendanceService.deleteAttendance(id);

}

}