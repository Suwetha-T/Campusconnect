package com.sms.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private String role;

    private long totalDepartments;

    private long totalFaculty;

    private long totalStudents;

    private long totalCourses;

    private long totalAttendance;

    private long totalAnnouncements;

    private long assignedCourseCount;

    private long pendingAttendanceCount;

    private double attendancePercentage;

    @Builder.Default
    private List<CourseResponse> assignedCourses = new ArrayList<>();

    @Builder.Default
    private List<MarksResponse> latestMarks = new ArrayList<>();

    @Builder.Default
    private List<StudentCourseResponse> enrolledCourses = new ArrayList<>();

    @Builder.Default
    private List<AnnouncementResponse> recentAnnouncements = new ArrayList<>();
}
