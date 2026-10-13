import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentModel } from '../../../core/models/student';
import { StudentService } from '../../../core/services/student.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class Profile implements OnInit {

  student: StudentModel | null = null;
  isLoading = true;
  errorMessage: string | null = null;
  private studentService = inject(StudentService);

  ngOnInit(): void {
    this.loadProfile();
  }

  loadProfile(): void {
    this.isLoading = true;
    this.errorMessage = null;

    this.studentService.getCurrentStudentProfile().subscribe({
      next: (data) => {
        this.student = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err?.error?.message || 'Unable to load your profile right now.';
        console.error(err);
      }
    });
  }

}
