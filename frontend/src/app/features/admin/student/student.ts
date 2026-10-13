import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { StudentModel } from '../../../core/models/student';
import { StudentService } from '../../../core/services/student.service';
import { DepartmentService } from '../../../core/services/department.service';
import { UserManagementService } from '../../../core/services/user-management.service';
import { UserModel } from '../../../core/models/user';

@Component({
  selector: 'app-student',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './student.html',
  styleUrl: './student.css'
})
export class Student implements OnInit {

  students: StudentModel[] = [];
  departments: any[] = [];
  users: UserModel[] = [];
  formErrors: string[] = [];

  searchKeyword = '';

  currentPage = 0;

  pageSize = 5;

  totalPages = 0;

  totalElements = 0;

  isEditMode = false;

  editingStudentId?: number;

  newStudent: StudentModel = {

    userId: undefined,

    departmentId: undefined,

    enrollmentNumber: '',

    firstName: '',

    lastName: '',

    phone: '',

    gender: '',

    dateOfBirth: '',

    address: '',

    profilePhoto: '',

    semester: 1,

    academicYear: ''

  };

  private studentService = inject(StudentService);
  private departmentService = inject(DepartmentService);
  private userService = inject(UserManagementService);

  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {

    this.loadStudents();
    this.loadDepartments();
    this.loadUsers();

  }

  loadDepartments(): void {
    this.departmentService.getAllDepartments().subscribe({
      next: data => this.departments = data,
      error: err => console.error(err)
    });
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: data => this.users = data.filter(u => u.role === 'STUDENT'),
      error: err => console.error(err)
    });
  }

  validateForm(): boolean {
    this.formErrors = [];
    if (!this.newStudent.userId) {
      this.formErrors.push('Please select a User Account.');
    }
    if (!this.newStudent.firstName?.trim()) {
      this.formErrors.push('First Name is required.');
    }
    if (!this.newStudent.lastName?.trim()) {
      this.formErrors.push('Last Name is required.');
    }
    if (!this.newStudent.departmentId) {
      this.formErrors.push('Please select a Department.');
    }
    return this.formErrors.length === 0;
  }

  loadStudents(): void {

    this.studentService
      .getStudentsPage(this.currentPage, this.pageSize)
      .subscribe({

        next: (page) => {

          this.students = page.content;

          this.totalPages = page.totalPages;

          this.totalElements = page.totalElements;

          this.cdr.detectChanges();

        },

        error: (err) => console.error(err)

      });

  }

  searchStudents(): void {

    if (!this.searchKeyword.trim()) {

      this.currentPage = 0;

      this.loadStudents();

      return;

    }

    this.studentService
      .searchStudents(this.searchKeyword)
      .subscribe({

        next: (data) => {

          this.students = data;

          this.totalPages = 1;

          this.totalElements = data.length;

          this.currentPage = 0;

          this.cdr.detectChanges();

        },

        error: (err) => console.error(err)

      });

  }

  previousPage(): void {

    if (this.currentPage > 0) {

      this.currentPage--;

      this.loadStudents();

    }

  }

  nextPage(): void {

    if (this.currentPage < this.totalPages - 1) {

      this.currentPage++;

      this.loadStudents();

    }

  }

  saveStudent(): void {

    if (!this.validateForm()) {

      return;

    }

    if (this.isEditMode) {

      this.studentService
        .updateStudent(this.editingStudentId!, this.newStudent)
        .subscribe({

          next: () => {

            this.resetForm();

            this.loadStudents();

          },

          error: (err) => console.error(err)

        });

      return;

    }

    this.studentService
      .addStudent(this.newStudent)
      .subscribe({

        next: () => {

          this.resetForm();

          this.currentPage = 0;

          this.loadStudents();

        },

        error: (err) => console.error(err)

      });

  }

  editStudent(student: StudentModel): void {

    this.isEditMode = true;

    this.editingStudentId = student.id;

    this.newStudent = { ...student };

  }

  deleteStudent(id: number): void {

    if (!confirm('Delete this student?')) {

      return;

    }

    this.studentService
      .deleteStudent(id)
      .subscribe({

        next: () => this.loadStudents(),

        error: (err) => console.error(err)

      });

  }

  cancelEdit(): void {

    this.resetForm();

  }

  private resetForm(): void {

    this.isEditMode = false;

    this.editingStudentId = undefined;

    this.newStudent = {

      userId: undefined,

      departmentId: undefined,

      enrollmentNumber: '',

      firstName: '',

      lastName: '',

      phone: '',

      gender: '',

      dateOfBirth: '',

      address: '',

      semester: 1,

      academicYear: ''

    };

  }

}