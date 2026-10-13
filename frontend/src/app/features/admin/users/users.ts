import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { UserModel } from '../../../core/models/user';
import { UserManagementService } from '../../../core/services/user-management.service';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './users.html',
  styleUrl: './users.css'
})
export class Users implements OnInit {

  users: UserModel[] = [];

  searchKeyword: string = '';

  currentPage = 0;

  pageSize = 5;

  totalPages = 0;

  totalElements = 0;

  isEditMode = false;

  editingUserId: number | null = null;

  showPassword = false;

  newUser: UserModel = {
    email: '',
    password: '',
    role: 'STUDENT'
  };

  roles = ['ADMIN', 'FACULTY', 'STUDENT'];

  private userService = inject(UserManagementService);

  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {

    this.loadUsers();

  }

  loadUsers(): void {

    this.userService
      .getUsersPage(this.currentPage, this.pageSize)
      .subscribe({

        next: (page) => {

          this.users = page.content;

          this.totalPages = page.totalPages;

          this.totalElements = page.totalElements;

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error('Error loading users:', err);

        }

      });

  }

  previousPage(): void {

    if (this.currentPage > 0) {

      this.currentPage--;

      this.loadUsers();

    }

  }

  nextPage(): void {

    if (this.currentPage < this.totalPages - 1) {

      this.currentPage++;

      this.loadUsers();

    }

  }

  searchUsers(): void {

    if (!this.searchKeyword.trim()) {

      this.currentPage = 0;

      this.loadUsers();

      return;

    }

    this.userService
      .searchUsers(this.searchKeyword)
      .subscribe({

        next: (data) => {

          this.users = data;

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

  saveUser(): void {

    if (!this.newUser.email?.trim() || !this.newUser.role) {
      return;
    }

    if (this.isEditMode && this.editingUserId !== null) {

      this.userService
        .updateUser(this.editingUserId, this.newUser)
        .subscribe({

          next: () => {

            alert('User Updated Successfully');

            this.resetForm();

            this.loadUsers();

          },

          error: (err) => {

            console.error(err);

            alert(err?.error?.message || 'Failed to update user');

          }

        });

    } else {

      if (!this.newUser.password?.trim()) {
        alert('Password is required');
        return;
      }

      this.userService
        .createUser(this.newUser)
        .subscribe({

          next: () => {

            alert('User Created Successfully');

            this.resetForm();

            this.loadUsers();

          },

          error: (err) => {

            console.error(err);

            alert(err?.error?.message || 'Failed to create user');

          }

        });

    }

  }

  editUser(user: UserModel): void {

    this.isEditMode = true;

    this.editingUserId = user.id!;

    this.newUser = {

      email: user.email,

      password: '',

      role: user.role

    };

  }

  toggleStatus(user: UserModel): void {

    const action = user.isActive ? 'deactivate' : 'activate';

    const confirmed = confirm(
      `Are you sure you want to ${action} this user?`
    );

    if (!confirmed) return;

    this.userService
      .toggleStatus(user.id!)
      .subscribe({

        next: () => {

          alert(`User ${action}d successfully`);

          this.loadUsers();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  deleteUser(id: number): void {

    const confirmed = confirm(
      'Are you sure you want to delete this user? This cannot be undone.'
    );

    if (!confirmed) return;

    this.userService
      .deleteUser(id)
      .subscribe({

        next: () => {

          alert('User Deleted Successfully');

          this.loadUsers();

        },

        error: (err) => {

          console.error(err);

          alert(err?.error?.message || 'Failed to delete user');

        }

      });

  }

  resetForm(): void {

    this.isEditMode = false;

    this.editingUserId = null;

    this.showPassword = false;

    this.newUser = {

      email: '',

      password: '',

      role: 'STUDENT'

    };

  }

  cancelEdit(): void {

    this.resetForm();

  }

  toggleShowPassword(): void {

    this.showPassword = !this.showPassword;

  }

}
