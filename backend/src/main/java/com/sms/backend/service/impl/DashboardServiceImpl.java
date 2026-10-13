package com.sms.backend.service.impl;

import com.sms.backend.dto.response.AnnouncementResponse;
import com.sms.backend.dto.response.CourseResponse;
import com.sms.backend.dto.response.DashboardResponse;
import com.sms.backend.dto.response.MarksResponse;
import com.sms.backend.dto.response.StudentCourseResponse;
import com.sms.backend.entity.Announcement;
import com.sms.backend.entity.Course;
import com.sms.backend.entity.Faculty;
import com.sms.backend.entity.Student;
import com.sms.backend.enums.AnnouncementTarget;
import com.sms.backend.enums.AttendanceStatus;
import com.sms.backend.exception.ResourceNotFoundException;
import com.sms.backend.mapper.AnnouncementMapper;
import com.sms.backend.mapper.CourseMapper;
import com.sms.backend.mapper.MarksMapper;
import com.sms.backend.mapper.StudentCourseMapper;
import com.sms.backend.repository.AnnouncementRepository;
import com.sms.backend.repository.AttendanceRepository;
import com.sms.backend.repository.CourseRepository;
import com.sms.backend.repository.DepartmentRepository;
import com.sms.backend.repository.FacultyRepository;
import com.sms.backend.repository.MarksRepository;
import com.sms.backend.repository.StudentCourseRepository;
import com.sms.backend.repository.StudentRepository;
import com.sms.backend.service.DashboardService;
import com.sms.backend.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AttendanceRepository attendanceRepository;
    private final AnnouncementRepository announcementRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final MarksRepository marksRepository;

    @Override
    public DashboardResponse getDashboardData() {

        if (SecurityUtils.hasRole("ADMIN")) {
            return buildAdminDashboard();
        }

        if (SecurityUtils.hasRole("FACULTY")) {
            return buildFacultyDashboard();
        }

        if (SecurityUtils.hasRole("STUDENT")) {
            return buildStudentDashboard();
        }

        throw new ResourceNotFoundException("Dashboard not available for this role");
    }

    private DashboardResponse buildAdminDashboard() {

        return DashboardResponse.builder()
                .role("ADMIN")
                .totalDepartments(departmentRepository.count())
                .totalFaculty(facultyRepository.count())
                .totalStudents(studentRepository.count())
                .totalCourses(courseRepository.count())
                .totalAttendance(attendanceRepository.count())
                .totalAnnouncements(announcementRepository.count())
                .recentAnnouncements(mapAnnouncements(
                        announcementRepository.findTop5ByOrderByCreatedAtDesc()))
                .build();
    }

    private DashboardResponse buildFacultyDashboard() {

        Faculty faculty = facultyRepository
                .findByUser_Email(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Faculty profile not found"));

        List<Course> courses =
                courseRepository.findByFaculty_Id(faculty.getId());

        List<CourseResponse> assignedCourses = courses.stream()
                .map(CourseMapper::toResponse)
                .toList();

        long pendingAttendance = courses.stream()
                .filter(course -> !attendanceRepository
                        .existsByStudentCourse_Course_IdAndAttendanceDate(
                                course.getId(),
                                LocalDate.now()))
                .count();

        return DashboardResponse.builder()
                .role("FACULTY")
                .assignedCourseCount(courses.size())
                .pendingAttendanceCount(pendingAttendance)
                .assignedCourses(assignedCourses)
                .recentAnnouncements(mapAnnouncements(
                        announcementRepository
                                .findTop5ByTargetInOrderByCreatedAtDesc(
                                        List.of(
                                                AnnouncementTarget.ALL,
                                                AnnouncementTarget.FACULTY))))
                .build();
    }

    private DashboardResponse buildStudentDashboard() {

        Student student = studentRepository
                .findByUser_Email(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student profile not found"));

        List<StudentCourseResponse> enrolledCourses =
                studentCourseRepository.findByStudent_Id(student.getId())
                        .stream()
                        .map(StudentCourseMapper::toResponse)
                        .toList();

        List<MarksResponse> latestMarks =
                marksRepository
                        .findTop5ByStudent_IdOrderByCreatedAtDesc(student.getId())
                        .stream()
                        .map(MarksMapper::toResponse)
                        .toList();

        return DashboardResponse.builder()
                .role("STUDENT")
                .attendancePercentage(calculateAttendancePercentage(student.getId()))
                .enrolledCourses(enrolledCourses)
                .latestMarks(latestMarks)
                .recentAnnouncements(mapAnnouncements(
                        announcementRepository
                                .findTop5ByTargetInOrderByCreatedAtDesc(
                                        List.of(
                                                AnnouncementTarget.ALL,
                                                AnnouncementTarget.STUDENT))))
                .build();
    }

    private double calculateAttendancePercentage(Long studentId) {

        var records = attendanceRepository.findByStudent_Id(studentId);

        if (records.isEmpty()) {
            return 0.0;
        }

        long attended = records.stream()
                .filter(record ->
                        record.getAttendanceStatus() == AttendanceStatus.PRESENT
                                || record.getAttendanceStatus()
                                == AttendanceStatus.LATE)
                .count();

        return Math.round((attended * 10000.0) / records.size()) / 100.0;
    }

    private List<AnnouncementResponse> mapAnnouncements(
            List<Announcement> announcements) {

        return announcements.stream()
                .map(AnnouncementMapper::toResponse)
                .toList();
    }
}
