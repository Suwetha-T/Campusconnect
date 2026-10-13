import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  getRole(): string {

    return localStorage.getItem('role') ?? '';

  }

  isAdmin(): boolean {

    return this.getRole() === 'ADMIN';

  }

  isFaculty(): boolean {

    return this.getRole() === 'FACULTY';

  }

  isStudent(): boolean {

    return this.getRole() === 'STUDENT';

  }

}