export interface DashboardModel {
  role?: string;
  totalDepartments?: number;
  totalFaculty?: number;
  totalStudents?: number;
  totalCourses?: number;
  totalAttendance?: number;
  totalAnnouncements?: number;
  assignedCourseCount?: number;
  pendingAttendanceCount?: number;
  assignedCourses?: any[];
  attendancePercentage?: number;
  enrolledCourses?: any[];
  latestMarks?: any[];
  recentAnnouncements?: any[];
}