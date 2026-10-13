import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { CourseModel } from '../models/course';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  private http = inject(HttpClient);

  private apiUrl = `${environment.apiUrl}/courses`;

  getAllCourses(): Observable<CourseModel[]> {

    return this.http.get<CourseModel[]>(this.apiUrl);

  }

  getCoursesPage(page: number, size: number): Observable<any> {

    return this.http.get<any>(
      `${this.apiUrl}/page?page=${page}&size=${size}`
    );

  }

  searchCourses(keyword: string): Observable<CourseModel[]> {

    return this.http.get<CourseModel[]>(
      `${this.apiUrl}/search?keyword=${keyword}`
    );

  }

  addCourse(course: CourseModel): Observable<CourseModel> {

    return this.http.post<CourseModel>(
      this.apiUrl,
      course
    );

  }

  updateCourse(id: number, course: CourseModel): Observable<CourseModel> {

    return this.http.put<CourseModel>(
      `${this.apiUrl}/${id}`,
      course
    );

  }

  deleteCourse(id: number): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );

  }

  getFacultyAssignedCourses(): Observable<CourseModel[]> {

    return this.http.get<CourseModel[]>(
      `${this.apiUrl}/me`
    );

  }

}