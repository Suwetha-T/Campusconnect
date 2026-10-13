import { Injectable, inject } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { DepartmentModel } from '../models/department';

import { Page } from '../models/page';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  private http = inject(HttpClient);

  private apiUrl = `${environment.apiUrl}/departments`;

  getAllDepartments(): Observable<DepartmentModel[]> {

    return this.http.get<DepartmentModel[]>(this.apiUrl);

  }

  getDepartmentsPage(
    page: number,
    size: number
  ): Observable<Page<DepartmentModel>> {

    return this.http.get<Page<DepartmentModel>>(
      `${this.apiUrl}/page?page=${page}&size=${size}`
    );

  }

  searchDepartments(keyword: string): Observable<DepartmentModel[]> {

    return this.http.get<DepartmentModel[]>(
      `${this.apiUrl}/search?keyword=${keyword}`
    );

  }

  addDepartment(department: DepartmentModel): Observable<DepartmentModel> {

    return this.http.post<DepartmentModel>(
      this.apiUrl,
      department
    );

  }

  updateDepartment(
    id: number,
    department: DepartmentModel
  ): Observable<DepartmentModel> {

    return this.http.put<DepartmentModel>(
      `${this.apiUrl}/${id}`,
      department
    );

  }

  deleteDepartment(id: number): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );

  }

}