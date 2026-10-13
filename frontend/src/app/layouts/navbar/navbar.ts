import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {

  private authService = inject(AuthService);
  private router = inject(Router);

  get email(): string {

    return this.authService.getEmail() ?? 'User';

  }

  logout(): void {

    this.authService.logout();
    this.router.navigate(['/login']);

  }

}
