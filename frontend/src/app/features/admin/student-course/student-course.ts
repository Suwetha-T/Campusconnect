import {
  Component,
  OnInit,
  inject
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { StudentCourseService } from '../../../core/services/student-course.service';
import { StudentCourseModel } from '../../../core/models/student-course';
import { StudentService } from '../../../core/services/student.service';
import { CourseService } from '../../../core/services/course.service';
import { StudentModel } from '../../../core/models/student';
import { CourseModel } from '../../../core/models/course';

@Component({
  selector: 'app-student-course',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './student-course.html',
  styleUrl: './student-course.css'
})
export class StudentCourse implements OnInit {

  studentCourses: StudentCourseModel[] = [];

  editing = false;

  studentList: StudentModel[] = [];

  courseList: CourseModel[] = [];
  formErrors: string[] = [];

  newStudentCourse: StudentCourseModel = {

    studentId: 0,
    courseId: 0,
    enrollmentDate: '',
    enrollmentStatus: 'ACTIVE'

  };

  private studentCourseService = inject(StudentCourseService);
  private studentService = inject(StudentService);
  private courseService = inject(CourseService);

  ngOnInit(): void {

    this.loadStudentCourses();
    this.loadStudents();
    this.loadCourses();

  }

  loadStudents(): void {
    this.studentService.getAllStudents().subscribe({
      next: data => this.studentList = data,
      error: err => console.error(err)
    });
  }

  loadCourses(): void {
    this.courseService.getAllCourses().subscribe({
      next: data => this.courseList = data,
      error: err => console.error(err)
    });
  }

  loadStudentCourses(): void {

    this.studentCourseService
      .getAllStudentCourses()
      .subscribe({

        next: data => {

          this.studentCourses = data;

        },

        error: err => console.error(err)

      });

  }

  validateForm(): boolean {
    this.formErrors = [];
    if (!this.newStudentCourse.studentId || this.newStudentCourse.studentId === 0) {
      this.formErrors.push('Please select a Student.');
    }
    if (!this.newStudentCourse.courseId || this.newStudentCourse.courseId === 0) {
      this.formErrors.push('Please select a Course.');
    }
    return this.formErrors.length === 0;
  }

  saveStudentCourse(): void {
    if (!this.validateForm()) {
      return;
    }

    if (this.editing) {

      this.studentCourseService
        .updateStudentCourse(
          this.newStudentCourse.id!,
          this.newStudentCourse
        )
        .subscribe({

          next: () => {

            this.resetForm();

            this.loadStudentCourses();

          },

          error: err => console.error(err)

        });

    } else {

      this.studentCourseService
        .addStudentCourse(this.newStudentCourse)
        .subscribe({

          next: () => {

            this.resetForm();

            this.loadStudentCourses();

          },

          error: err => console.error(err)

        });

    }

  }

  editStudentCourse(studentCourse: StudentCourseModel): void {

    this.editing = true;

    this.newStudentCourse = {

      ...studentCourse

    };

  }

  deleteStudentCourse(id: number): void {

    if (!confirm('Delete this enrollment?')) {

      return;

    }

    this.studentCourseService
      .deleteStudentCourse(id)
      .subscribe({

        next: () => {

          this.loadStudentCourses();

        },

        error: err => console.error(err)

      });

  }

  resetForm(): void {

    this.editing = false;

    this.newStudentCourse = {

      studentId: 0,
      courseId: 0,
      enrollmentDate: '',
      enrollmentStatus: 'ACTIVE'

    };

  }

}