export interface MarksModel {

  id?: number;

  studentId?: number;

  studentName?: string;

  courseId?: number;

  courseName?: string;

  examType: string;

  marksObtained: number;

  maximumMarks: number;

  createdAt?: string;

  updatedAt?: string;

}