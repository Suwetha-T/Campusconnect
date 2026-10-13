import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AttendanceModel } from '../../../core/models/attendance';
import { AttendanceService } from '../../../core/services/attendance.service';

@Component({
  selector: 'app-attendance',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './attendance.html',
  styleUrl: './attendance.css'
})
export class Attendance implements OnInit {

  attendanceList: AttendanceModel[] = [];
  private attendanceService = inject(AttendanceService);

  ngOnInit(): void {
    this.attendanceService.getCurrentStudentAttendance().subscribe({
      next: (data) => {
        this.attendanceList = data;
      },
      error: (err) => console.error(err)
    });
  }

}
