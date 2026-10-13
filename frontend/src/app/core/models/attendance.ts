export interface AttendanceModel {

  id?: number;

  studentId?: number;

  studentName?: string;

  courseId?: number;

  courseName?: string;

  attendanceDate: string;

  attendanceStatus: string;

  createdAt?: string;

  updatedAt?: string;

}