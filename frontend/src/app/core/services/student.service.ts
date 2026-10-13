import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { StudentModel } from '../models/student';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  private http = inject(HttpClient);

  private apiUrl = `${environment.apiUrl}/students`;

  getAllStudents(): Observable<StudentModel[]> {

    return this.http.get<StudentModel[]>(this.apiUrl);

  }

  getStudentsPage(page: number, size: number): Observable<any> {

    return this.http.get<any>(
      `${this.apiUrl}/page?page=${page}&size=${size}`
    );

  }

  searchStudents(keyword: string): Observable<StudentModel[]> {

    return this.http.get<StudentModel[]>(
      `${this.apiUrl}/search?keyword=${keyword}`
    );

  }

  addStudent(student: StudentModel): Observable<StudentModel> {

    return this.http.post<StudentModel>(
      this.apiUrl,
      student
    );

  }

  updateStudent(
    id: number,
    student: StudentModel
  ): Observable<StudentModel> {

    return this.http.put<StudentModel>(
      `${this.apiUrl}/${id}`,
      student
    );

  }

  deleteStudent(id: number): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );

  }

  getCurrentStudentProfile(): Observable<StudentModel> {

    return this.http.get<StudentModel>(
      `${this.apiUrl}/me`
    );

  }

}