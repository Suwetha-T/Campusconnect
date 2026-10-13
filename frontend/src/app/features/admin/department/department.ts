import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { DepartmentModel } from '../../../core/models/department';
import { DepartmentService } from '../../../core/services/department.service';

@Component({
  selector: 'app-department',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './department.html',
  styleUrl: './department.css'
})
export class Department implements OnInit {

  departments: DepartmentModel[] = [];

  searchKeyword: string = '';

  currentPage = 0;

  pageSize = 5;

  totalPages = 0;

  totalElements = 0;

  isEditMode = false;

  editingDepartmentId: number | null = null;

  newDepartment: DepartmentModel = {
    name: '',
    code: '',
    description: ''
  };

  private departmentService = inject(DepartmentService);

  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {

    this.loadDepartments();

  }

  loadDepartments(): void {

    this.departmentService
      .getDepartmentsPage(this.currentPage, this.pageSize)
      .subscribe({

        next: (page) => {

          console.log(page);

          this.departments = page.content;

          this.totalPages = page.totalPages;

          this.totalElements = page.totalElements;

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error('Error loading departments:', err);

        }

      });

  }

  previousPage(): void {

    if (this.currentPage > 0) {

      this.currentPage--;

      this.loadDepartments();

    }

  }

  nextPage(): void {

    if (this.currentPage < this.totalPages - 1) {

      this.currentPage++;

      this.loadDepartments();

    }

  }

  searchDepartments(): void {

    if (!this.searchKeyword.trim()) {

      this.currentPage = 0;

      this.loadDepartments();

      return;

    }

    this.departmentService
      .searchDepartments(this.searchKeyword)
      .subscribe({

        next: (data) => {

          this.departments = data;

          this.totalPages = 1;

          this.totalElements = data.length;

          this.currentPage = 0;

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error('Search Error:', err);

        }

      });

  }

  saveDepartment(): void {

    if (!this.newDepartment.name?.trim()) {
      return;
    }

    if (this.isEditMode && this.editingDepartmentId !== null) {

      this.departmentService
        .updateDepartment(this.editingDepartmentId, this.newDepartment)
        .subscribe({

          next: () => {

            alert('Department Updated Successfully');

            this.resetForm();

            this.loadDepartments();

          },

          error: (err) => {

            console.error(err);

          }

        });

    } else {

      this.departmentService
        .addDepartment(this.newDepartment)
        .subscribe({

          next: () => {

            alert('Department Added Successfully');

            this.resetForm();

            this.loadDepartments();

          },

          error: (err) => {

            console.error(err);

          }

        });

    }

  }

  editDepartment(department: DepartmentModel): void {

    this.isEditMode = true;

    this.editingDepartmentId = department.id!;

    this.newDepartment = {

      name: department.name,

      code: department.code,

      description: department.description

    };

  }

  deleteDepartment(id: number): void {

    const confirmed = confirm(
      'Are you sure you want to delete this department?'
    );

    if (!confirmed) {
      return;
    }

    this.departmentService
      .deleteDepartment(id)
      .subscribe({

        next: () => {

          alert('Department Deleted Successfully');

          this.loadDepartments();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  resetForm(): void {

    this.isEditMode = false;

    this.editingDepartmentId = null;

    this.newDepartment = {

      name: '',

      code: '',

      description: ''

    };

  }
  cancelEdit(): void {

  this.resetForm();

}

}