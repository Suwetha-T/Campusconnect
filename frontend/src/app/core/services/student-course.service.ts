import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { StudentCourseModel } from '../models/student-course';

@Injectable({
  providedIn: 'root'
})
export class StudentCourseService {

  private http = inject(HttpClient);

  private apiUrl =
    `${environment.apiUrl}/student-courses`;

  getAllStudentCourses(): Observable<StudentCourseModel[]> {

    return this.http.get<StudentCourseModel[]>(this.apiUrl);

  }

  addStudentCourse(
    studentCourse: StudentCourseModel
  ): Observable<StudentCourseModel> {

    return this.http.post<StudentCourseModel>(
      this.apiUrl,
      studentCourse
    );

  }

  updateStudentCourse(
    id: number,
    studentCourse: StudentCourseModel
  ): Observable<StudentCourseModel> {

    return this.http.put<StudentCourseModel>(
      `${this.apiUrl}/${id}`,
      studentCourse
    );

  }

  deleteStudentCourse(
    id: number
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );

  }

  getCurrentStudentCourses(): Observable<StudentCourseModel[]> {

    return this.http.get<StudentCourseModel[]>(
      `${this.apiUrl}/me`
    );

  }

  getStudentCoursesByStudentId(studentId: number): Observable<StudentCourseModel[]> {
    return this.http.get<StudentCourseModel[]>(
      `${this.apiUrl}/student/${studentId}`
    );
  }

}