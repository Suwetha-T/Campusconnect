import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AttendanceModel } from '../../../core/models/attendance';
import { AttendanceService } from '../../../core/services/attendance.service';
import { StudentCourseService } from '../../../core/services/student-course.service';
import { StudentCourseModel } from '../../../core/models/student-course';
import { StudentService } from '../../../core/services/student.service';
import { StudentModel } from '../../../core/models/student';
@Component({
  selector: 'app-attendance',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './attendance.html',
  styleUrl: './attendance.css'
})
export class Attendance implements OnInit {

  attendanceList: AttendanceModel[] = [];

  studentList: StudentModel[] = [];

  studentCourseList: StudentCourseModel[] = [];

  searchKeyword = '';
  formErrors: string[] = [];

  currentPage = 0;

  pageSize = 5;

  totalPages = 0;

  totalElements = 0;

  editingAttendance = false;

  newAttendance: AttendanceModel = {

    id: undefined,

    studentId: 0,

    courseId: 0,

    attendanceDate: '',

    attendanceStatus: ''

  };

  private attendanceService = inject(AttendanceService);
  private studentCourseService = inject(StudentCourseService);
  private studentService = inject(StudentService);

  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {

    this.loadAttendance();
    this.loadStudents();

  }

  loadStudents(): void {
    this.studentService.getAllStudents().subscribe({
      next: (data) => {
        this.studentList = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  onStudentSelected(): void {
    if (!this.newAttendance.studentId) {
      this.studentCourseList = [];
      this.newAttendance.courseId = 0;
      return;
    }
    
    this.studentCourseService.getStudentCoursesByStudentId(this.newAttendance.studentId).subscribe({
      next: (data) => {
        this.studentCourseList = data;
        this.newAttendance.courseId = 0; // reset course selection
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  loadAttendance(): void {

    this.attendanceService
      .getAttendancePage(this.currentPage, this.pageSize)
      .subscribe({

        next: (page) => {

          this.attendanceList = page.content;

          this.totalPages = page.totalPages;

          this.totalElements = page.totalElements;

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  previousPage(): void {

    if (this.currentPage > 0) {

      this.currentPage--;

      this.loadAttendance();

    }

  }

  nextPage(): void {

    if (this.currentPage < this.totalPages - 1) {

      this.currentPage++;

      this.loadAttendance();

    }

  }

  searchAttendance(): void {

    if (!this.searchKeyword.trim()) {

      this.currentPage = 0;

      this.loadAttendance();

      return;

    }

    this.attendanceService
      .searchAttendance(this.searchKeyword)
      .subscribe({

        next: (data) => {

          this.attendanceList = data;

          this.totalPages = 1;

          this.totalElements = data.length;

          this.currentPage = 0;

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  saveAttendance(): void {

    this.formErrors = [];
    if (!this.newAttendance.studentId || this.newAttendance.studentId === 0) {
      this.formErrors.push('Please select a Student.');
    }
    if (!this.newAttendance.courseId || this.newAttendance.courseId === 0) {
      this.formErrors.push('Please select a Course.');
    }
    if (!this.newAttendance.attendanceDate?.trim()) {
      this.formErrors.push('Date is required.');
    }
    if (!this.newAttendance.attendanceStatus?.trim()) {
      this.formErrors.push('Please select a Status.');
    }
    if (this.formErrors.length > 0) {
      return;
    }

    if (this.editingAttendance) {

      this.attendanceService
        .updateAttendance(this.newAttendance.id!, this.newAttendance)
        .subscribe({

          next: () => {

            this.resetForm();

            this.loadAttendance();

          },

          error: (err) => {

            console.error(err);

          }

        });

    } else {

      this.attendanceService
        .addAttendance(this.newAttendance)
        .subscribe({

          next: () => {

            this.resetForm();

            this.currentPage = 0;

            this.loadAttendance();

          },

          error: (err) => {

            console.error(err);

          }

        });

    }

  }

  editAttendance(attendance: AttendanceModel): void {

    this.editingAttendance = true;

    this.newAttendance = { ...attendance };

    if (this.newAttendance.studentId) {
       this.onStudentSelected();
       // we need to set the courseId again after the courses are loaded, 
       // but onStudentSelected is async, so we just load it directly.
       this.studentCourseService.getStudentCoursesByStudentId(this.newAttendance.studentId).subscribe({
          next: (data) => {
             this.studentCourseList = data;
             this.newAttendance.courseId = attendance.courseId;
             this.cdr.detectChanges();
          }
       });
    }

  }

  deleteAttendance(id: number): void {

    if (!confirm('Delete this attendance record?')) {

      return;

    }

    this.attendanceService
      .deleteAttendance(id)
      .subscribe({

        next: () => {

          this.loadAttendance();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  resetForm(): void {

    this.editingAttendance = false;

    this.newAttendance = {

      id: undefined,

      studentId: 0,

      courseId: 0,

      attendanceDate: '',

      attendanceStatus: ''

    };

  }

}