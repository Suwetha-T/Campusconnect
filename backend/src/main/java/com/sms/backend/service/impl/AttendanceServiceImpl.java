package com.sms.backend.service.impl;

import com.sms.backend.dto.request.AttendanceRequest;
import com.sms.backend.dto.response.AttendanceResponse;
import com.sms.backend.entity.Attendance;
import com.sms.backend.entity.StudentCourse;
import com.sms.backend.exception.ResourceNotFoundException;
import com.sms.backend.mapper.AttendanceMapper;
import com.sms.backend.repository.AttendanceRepository;
import com.sms.backend.repository.StudentCourseRepository;
import com.sms.backend.repository.StudentRepository;
import com.sms.backend.service.AttendanceService;
import com.sms.backend.util.SecurityUtils;
import com.sms.backend.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final StudentRepository studentRepository;

    @Override
    public AttendanceResponse createAttendance(AttendanceRequest request) {

        StudentCourse studentCourse = studentCourseRepository.findByStudent_IdAndCourse_Id(request.getStudentId(), request.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student Course not found for given student and course."));

        Attendance attendance = AttendanceMapper.toEntity(request, studentCourse);

        Attendance savedAttendance = attendanceRepository.save(attendance);

        return AttendanceMapper.toResponse(savedAttendance);
    }

    @Override
    public AttendanceResponse getAttendanceById(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attendance not found."));

        return AttendanceMapper.toResponse(attendance);
    }

    @Override
    public List<AttendanceResponse> getAllAttendance() {

        return attendanceRepository.findAll()
                .stream()
                .map(AttendanceMapper::toResponse)
                .toList();

    }

    @Override
    public AttendanceResponse updateAttendance(Long id,
                                               AttendanceRequest request) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attendance not found."));

        StudentCourse studentCourse = studentCourseRepository.findByStudent_IdAndCourse_Id(request.getStudentId(), request.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student Course not found for given student and course."));

        attendance.setStudent(studentCourse.getStudent());
        attendance.setStudentCourse(studentCourse);
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setAttendanceStatus(request.getAttendanceStatus());

        Attendance updatedAttendance = attendanceRepository.save(attendance);

        return AttendanceMapper.toResponse(updatedAttendance);

    }

    @Override
    public void deleteAttendance(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attendance not found."));

        attendanceRepository.delete(attendance);

    }

    @Override
    public List<AttendanceResponse> searchAttendance(String keyword) {

        return attendanceRepository
                .findByStudent_FirstNameContainingIgnoreCase(keyword)
                .stream()
                .map(AttendanceMapper::toResponse)
                .toList();

    }

    @Override
    public Page<AttendanceResponse> getAttendancePage(Pageable pageable) {

        return attendanceRepository
                .findAll(pageable)
                .map(AttendanceMapper::toResponse);

    }

    @Override
    public List<AttendanceResponse> getCurrentStudentAttendance() {

        Student student = studentRepository
                .findByUser_Email(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student profile not found."));

        return attendanceRepository.findByStudent_Id(student.getId())
                .stream()
                .map(AttendanceMapper::toResponse)
                .toList();
    }
}