import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';

import { RoleService } from '../../core/services/role.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css'
})
export class Sidebar {

  roleService = inject(RoleService);

  get basePath(): string {
    if (this.roleService.isStudent()) return '/student';
    if (this.roleService.isFaculty()) return '/faculty';
    return '/admin';
  }

}