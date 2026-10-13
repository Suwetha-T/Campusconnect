import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef
} from '@angular/core';

import { CommonModule } from '@angular/common';

import { DashboardModel } from '../../../core/models/dashboard';
import { DashboardService } from '../../../core/services/dashboard.service';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit {

  dashboard: DashboardModel = {} as DashboardModel;
  profileNotFound = false;
  userRole: string | null = null;

  private dashboardService = inject(DashboardService);
  authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.userRole = this.authService.getRole();
    this.loadDashboard();
  }

  loadDashboard(): void {

    this.dashboardService.getDashboardData().subscribe({

      next: (data) => {

        console.log("Dashboard Data:", data);

        this.dashboard = { ...data };
        this.profileNotFound = false;
        this.cdr.detectChanges();

      },

      error: (err) => {

        console.error(err);
        // 404 means profile not linked yet
        if (err.status === 404 || err.status === 400) {
          this.profileNotFound = true;
        }
        this.cdr.detectChanges();

      }

    });

  }

}