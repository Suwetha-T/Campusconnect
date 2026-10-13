import { Injectable, inject } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { AttendanceModel } from '../models/attendance';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AttendanceService {

  private http = inject(HttpClient);

  private apiUrl = `${environment.apiUrl}/attendance`;

  getAllAttendance(): Observable<AttendanceModel[]> {

    return this.http.get<AttendanceModel[]>(this.apiUrl);

  }

  getAttendancePage(page: number, size: number): Observable<any> {

    return this.http.get<any>(
      `${this.apiUrl}/page?page=${page}&size=${size}`
    );

  }

  searchAttendance(keyword: string): Observable<AttendanceModel[]> {

    return this.http.get<AttendanceModel[]>(
      `${this.apiUrl}/search?keyword=${keyword}`
    );

  }

  addAttendance(attendance: AttendanceModel): Observable<AttendanceModel> {

    return this.http.post<AttendanceModel>(
      this.apiUrl,
      attendance
    );

  }

  updateAttendance(id: number, attendance: AttendanceModel): Observable<AttendanceModel> {

    return this.http.put<AttendanceModel>(
      `${this.apiUrl}/${id}`,
      attendance
    );

  }

  deleteAttendance(id: number): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );

  }

  getCurrentStudentAttendance(): Observable<AttendanceModel[]> {

    return this.http.get<AttendanceModel[]>(
      `${this.apiUrl}/me`
    );

  }

}