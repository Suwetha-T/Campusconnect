import { Injectable, inject } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { UserModel } from '../models/user';

import { Page } from '../models/page';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserManagementService {

  private http = inject(HttpClient);

  private apiUrl = `${environment.apiUrl}/users`;

  getUsersPage(page: number, size: number): Observable<Page<UserModel>> {

    return this.http.get<Page<UserModel>>(
      `${this.apiUrl}/page?page=${page}&size=${size}`
    );

  }

  getAllUsers(): Observable<UserModel[]> {

    return this.http.get<UserModel[]>(this.apiUrl);

  }

  createUser(user: UserModel): Observable<UserModel> {

    return this.http.post<UserModel>(this.apiUrl, user);

  }

  updateUser(id: number, user: UserModel): Observable<UserModel> {

    return this.http.put<UserModel>(`${this.apiUrl}/${id}`, user);

  }

  toggleStatus(id: number): Observable<void> {

    return this.http.patch<void>(`${this.apiUrl}/${id}/toggle-status`, {});

  }

  deleteUser(id: number): Observable<void> {

    return this.http.delete<void>(`${this.apiUrl}/${id}`);

  }

  searchUsers(keyword: string): Observable<UserModel[]> {

    return this.http.get<UserModel[]>(
      `${this.apiUrl}/search?keyword=${keyword}`
    );

  }

}
