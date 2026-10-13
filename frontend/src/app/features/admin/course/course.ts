import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { CourseModel } from '../../../core/models/course';
import { CourseService } from '../../../core/services/course.service';
import { DepartmentService } from '../../../core/services/department.service';
import { FacultyService } from '../../../core/services/faculty.service';

@Component({
  selector: 'app-course',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './course.html',
  styleUrl: './course.css'
})
export class Course implements OnInit {

  courses: CourseModel[] = [];
  departments: any[] = [];
  faculties: any[] = [];
  formErrors: string[] = [];

  searchKeyword = '';

  currentPage = 0;

  pageSize = 5;

  totalPages = 0;

  totalElements = 0;

  editingCourse = false;

  newCourse: CourseModel = {

    id: undefined,

    departmentId: 0,

    facultyId: 0,

    code: '',

    name: '',

    description: '',

    credits: 1,

    semester: 1

  };

  private courseService = inject(CourseService);
  private departmentService = inject(DepartmentService);
  private facultyService = inject(FacultyService);

  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadCourses();
    this.loadDepartments();
    this.loadFaculties();
  }

  loadDepartments(): void {
    this.departmentService.getAllDepartments().subscribe({
      next: data => this.departments = data,
      error: err => console.error(err)
    });
  }

  loadFaculties(): void {
    this.facultyService.getAllFaculty().subscribe({
      next: data => this.faculties = data,
      error: err => console.error(err)
    });
  }

  validateForm(): boolean {
    this.formErrors = [];
    if (!this.newCourse.name?.trim()) {
      this.formErrors.push('Course Name is required.');
    }
    if (!this.newCourse.code?.trim()) {
      this.formErrors.push('Course Code is required.');
    }
    if (!this.newCourse.departmentId || this.newCourse.departmentId === 0) {
      this.formErrors.push('Please select a Department.');
    }
    if (!this.newCourse.facultyId || this.newCourse.facultyId === 0) {
      this.formErrors.push('Please select a Faculty.');
    }
    return this.formErrors.length === 0;
  }

  loadCourses(): void {

    this.courseService
      .getCoursesPage(this.currentPage, this.pageSize)
      .subscribe({

        next: (page) => {

          this.courses = page.content;

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

      this.loadCourses();

    }

  }

  nextPage(): void {

    if (this.currentPage < this.totalPages - 1) {

      this.currentPage++;

      this.loadCourses();

    }

  }

  searchCourses(): void {

    if (!this.searchKeyword.trim()) {

      this.currentPage = 0;

      this.loadCourses();

      return;

    }

    this.courseService
      .searchCourses(this.searchKeyword)
      .subscribe({

        next: (data) => {

          this.courses = data;

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

  saveCourse(): void {

    if (!this.validateForm()) {

      return;

    }

    if (this.editingCourse) {

      this.courseService
        .updateCourse(this.newCourse.id!, this.newCourse)
        .subscribe({

          next: () => {

            this.resetForm();

            this.loadCourses();

          },

          error: (err) => {

            console.error(err);

          }

        });

    } else {

      this.courseService
        .addCourse(this.newCourse)
        .subscribe({

          next: () => {

            this.resetForm();

            this.currentPage = 0;

            this.loadCourses();

          },

          error: (err) => {

            console.error(err);

          }

        });

    }

  }

  editCourse(course: CourseModel): void {

    this.editingCourse = true;

    this.newCourse = { ...course };

  }

  deleteCourse(id: number): void {

    if (!confirm('Delete this course?')) {

      return;

    }

    this.courseService
      .deleteCourse(id)
      .subscribe({

        next: () => {

          this.loadCourses();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  resetForm(): void {

    this.editingCourse = false;

    this.newCourse = {

      id: undefined,

      departmentId: 0,

      facultyId: 0,

      code: '',

      name: '',

      description: '',

      credits: 1,

      semester: 1

    };

  }

}