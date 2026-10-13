import { Injectable, inject } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { FacultyModel } from '../models/faculty';
import { Page } from '../models/page';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FacultyService {

  private http = inject(HttpClient);

  private apiUrl = `${environment.apiUrl}/faculty`;

  getAllFaculty(): Observable<FacultyModel[]> {

    return this.http.get<FacultyModel[]>(this.apiUrl);

  }

  getFacultyPage(
    page: number,
    size: number
  ): Observable<Page<FacultyModel>> {

    return this.http.get<Page<FacultyModel>>(
      `${this.apiUrl}/page?page=${page}&size=${size}`
    );

  }

  searchFaculty(keyword: string): Observable<FacultyModel[]> {

    return this.http.get<FacultyModel[]>(
      `${this.apiUrl}/search?keyword=${keyword}`
    );

  }

  addFaculty(
    faculty: FacultyModel
  ): Observable<FacultyModel> {

    return this.http.post<FacultyModel>(
      this.apiUrl,
      faculty
    );

  }

  updateFaculty(
    id: number,
    faculty: FacultyModel
  ): Observable<FacultyModel> {

    return this.http.put<FacultyModel>(
      `${this.apiUrl}/${id}`,
      faculty
    );

  }

  deleteFaculty(
    id: number
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );

  }

}