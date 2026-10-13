export interface CourseModel {

  id?: number;

  departmentId?: number;

  departmentName?: string;

  facultyId?: number;

  facultyName?: string;

  code: string;

  name: string;

  description: string;

  credits: number;

  semester: number;

}