import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { LoginRequest } from '../models/auth/login-request';
import { AuthResponse } from '../models/auth/auth-response';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private http = inject(HttpClient);

  private apiUrl = `${environment.apiUrl.replace('/v1', '')}/auth`;

  login(request: LoginRequest): Observable<AuthResponse> {

    return this.http.post<AuthResponse>(
      `${this.apiUrl}/login`,
      request
    );

  }

  logout(): void {

    localStorage.removeItem('token');
    localStorage.removeItem('email');
    localStorage.removeItem('role');

  }

  saveUser(response: AuthResponse): void {

    localStorage.setItem('token', response.token);
    localStorage.setItem('email', response.email);
    localStorage.setItem('role', response.role);

  }

  getToken(): string | null {

    return localStorage.getItem('token');

  }

  getRole(): string | null {

    return localStorage.getItem('role');

  }

  getEmail(): string | null {

    return localStorage.getItem('email');

  }

  isLoggedIn(): boolean {

    const token = this.getToken();
    if (!token) return false;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const exp = payload.exp * 1000;
      
      if (Date.now() >= exp) {
        this.logout();
        return false;
      }
      return true;
    } catch (e) {
      return false;
    }

  }

  getCurrentUser(): { email: string; role: string } | null {
    if (!this.isLoggedIn()) return null;
    return {
      email: this.getEmail() || '',
      role: this.getRole() || ''
    };
  }

  getHomeRoute(): string {
    const role = this.getRole();
    if (role === 'ADMIN') return '/admin';
    if (role === 'FACULTY') return '/faculty';
    if (role === 'STUDENT') return '/student';
    return '/login';
  }

}