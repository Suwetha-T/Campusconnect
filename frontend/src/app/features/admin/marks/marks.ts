import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';

import { CommonModule } from '@angular/common';

import { FormsModule } from '@angular/forms';

import { MarksModel } from '../../../core/models/marks';

import { MarksService } from '../../../core/services/marks.service';
import { StudentCourseService } from '../../../core/services/student-course.service';
import { StudentCourseModel } from '../../../core/models/student-course';
import { StudentService } from '../../../core/services/student.service';
import { StudentModel } from '../../../core/models/student';

@Component({
  selector: 'app-marks',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './marks.html',
  styleUrl: './marks.css'
})
export class Marks implements OnInit {

  marksList: MarksModel[] = [];

  studentList: StudentModel[] = [];

  studentCourseList: StudentCourseModel[] = [];

  formErrors: string[] = [];

  newMarks: MarksModel = {
    studentId: 0,
    courseId: 0,
    examType: '',
    marksObtained: 0,
    maximumMarks: 100
  };

  private marksService = inject(MarksService);
  private studentCourseService = inject(StudentCourseService);
  private studentService = inject(StudentService);

  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadMarks();
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
    if (!this.newMarks.studentId) {
      this.studentCourseList = [];
      this.newMarks.courseId = 0;
      return;
    }
    
    this.studentCourseService.getStudentCoursesByStudentId(this.newMarks.studentId).subscribe({
      next: (data) => {
        this.studentCourseList = data;
        this.newMarks.courseId = 0;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  loadMarks(): void {
    this.marksService.getAllMarks().subscribe({
      next: (data) => {
        console.log("Marks:", data);
        this.marksList = [...data];
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  validateForm(): boolean {
    this.formErrors = [];
    if (!this.newMarks.studentId || this.newMarks.studentId === 0) {
      this.formErrors.push('Please select a Student.');
    }
    if (!this.newMarks.courseId || this.newMarks.courseId === 0) {
      this.formErrors.push('Please select a Course.');
    }
    if (!this.newMarks.examType?.trim()) {
      this.formErrors.push('Exam Type is required.');
    }
    if (this.newMarks.marksObtained === undefined || this.newMarks.marksObtained === null) {
      this.formErrors.push('Marks Obtained is required.');
    }
    if (!this.newMarks.maximumMarks || this.newMarks.maximumMarks <= 0) {
      this.formErrors.push('Maximum Marks must be greater than 0.');
    }
    return this.formErrors.length === 0;
  }

  saveMarks(): void {
    if (!this.validateForm()) {
      return;
    }

    this.marksService.addMarks(this.newMarks).subscribe({

      next: () => {

        alert("Marks Added Successfully");

        this.newMarks = {

          studentId: 0,
          courseId: 0,

          examType: '',

          marksObtained: 0,

          maximumMarks: 100

        };

        this.loadMarks();

      },

      error: (err) => {

        console.error(err);

      }

    });

  }

}