import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login implements OnInit {

  loginForm: FormGroup;
  errorMessage = '';
  loading = false;

  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  constructor() {

    this.loginForm = this.fb.group({

      email: ['', [Validators.required, Validators.email]],

      password: ['', Validators.required]

    });

  }

  ngOnInit(): void {

    if (this.authService.isLoggedIn()) {

      this.router.navigate([this.authService.getHomeRoute()]);

    }

  }

  onSubmit(): void {

    if (this.loginForm.invalid) {

      this.loginForm.markAllAsTouched();

      return;

    }

    this.errorMessage = '';
    this.loading = true;

    this.authService.login(this.loginForm.value).subscribe({

      next: (response) => {

        this.loading = false;
        this.authService.saveUser(response);
        this.router.navigate([this.authService.getHomeRoute()]);

      },

      error: (err) => {

        this.loading = false;
        this.errorMessage =
          err.error?.message ?? 'Invalid email or password';

      }

    });

  }

}
