import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { FacultyModel } from '../../../core/models/faculty';
import { FacultyService } from '../../../core/services/faculty.service';
import { DepartmentService } from '../../../core/services/department.service';
import { UserManagementService } from '../../../core/services/user-management.service';
import { UserModel } from '../../../core/models/user';

@Component({
  selector: 'app-faculty',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './faculty.html',
  styleUrl: './faculty.css'
})
export class Faculty implements OnInit {

  facultyList: FacultyModel[] = [];

  searchKeyword = '';

  currentPage = 0;

  pageSize = 5;

  totalPages = 0;

  totalElements = 0;

  isEditing = false;

  editingId?: number;

  newFaculty: FacultyModel = this.createEmptyFaculty();

  departments: any[] = [];
  users: UserModel[] = [];
  formErrors: string[] = [];
  private facultyService = inject(FacultyService);
  private departmentService = inject(DepartmentService);
  private userService = inject(UserManagementService);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadFaculty();
    this.loadDepartments();
    this.loadUsers();
  }

  loadDepartments(): void {
    this.departmentService.getAllDepartments().subscribe({
      next: (deps) => (this.departments = deps),
      error: (err) => console.error(err)
    });
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: data => this.users = data.filter(u => u.role === 'FACULTY'),
      error: err => console.error(err)
    });
  }

  validateForm(): boolean {
    this.formErrors = [];
    if (!this.newFaculty.userId) {
      this.formErrors.push('Please select a User Account.');
    }
    if (!this.newFaculty.employeeCode?.trim()) {
      this.formErrors.push('Employee Code is required.');
    }
    if (!this.newFaculty.firstName?.trim()) {
      this.formErrors.push('First Name is required.');
    }
    if (!this.newFaculty.departmentId || this.newFaculty.departmentId === 0) {
      this.formErrors.push('Please select a Department.');
    }
    return this.formErrors.length === 0;
  }

  private createEmptyFaculty(): FacultyModel {

    return {

      userId: 0,

      userEmail: '',

      departmentId: 0,

      employeeCode: '',

      firstName: '',

      lastName: '',

      phone: '',

      gender: '',

      dateOfBirth: '',

      address: '',

      profilePhoto: ''

    };

  }

  loadFaculty(): void {

    this.facultyService
      .getFacultyPage(this.currentPage, this.pageSize)
      .subscribe({

        next: (page) => {

          this.facultyList = page.content;

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

      this.loadFaculty();

    }

  }

  nextPage(): void {

    if (this.currentPage < this.totalPages - 1) {

      this.currentPage++;

      this.loadFaculty();

    }

  }

  searchFaculty(): void {

    if (!this.searchKeyword.trim()) {

      this.currentPage = 0;

      this.loadFaculty();

      return;

    }

    this.facultyService
      .searchFaculty(this.searchKeyword)
      .subscribe({

        next: (data) => {

          this.facultyList = data;

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

  saveFaculty(): void {

    if (!this.validateForm()) {

      return;

    }

    if (this.isEditing) {

      this.updateFaculty();

      return;

    }

    this.facultyService
      .addFaculty(this.newFaculty)
      .subscribe({

        next: () => {

          alert('Faculty Added Successfully');

          this.cancelEdit();

          this.currentPage = 0;

          this.loadFaculty();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  editFaculty(faculty: FacultyModel): void {

    this.isEditing = true;

    this.editingId = faculty.id;

    this.newFaculty = { ...faculty };

  }

  updateFaculty(): void {

    if (!this.editingId) {

      return;

    }

    this.facultyService
      .updateFaculty(this.editingId, this.newFaculty)
      .subscribe({

        next: () => {

          alert('Faculty Updated Successfully');

          this.cancelEdit();

          this.loadFaculty();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  deleteFaculty(id: number): void {

    if (!confirm('Delete this faculty?')) {

      return;

    }

    this.facultyService
      .deleteFaculty(id)
      .subscribe({

        next: () => {

          alert('Faculty Deleted');

          this.loadFaculty();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  cancelEdit(): void {

    this.isEditing = false;

    this.editingId = undefined;

    this.newFaculty = this.createEmptyFaculty();

  }

}