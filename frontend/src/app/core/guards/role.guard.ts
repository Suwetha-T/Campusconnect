import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

import { RoleService } from '../services/role.service';
import { AuthService } from '../services/auth.service';

export const adminGuard: CanActivateFn = () => {

  const router = inject(Router);

  const roleService = inject(RoleService);

  const authService = inject(AuthService);

  if (roleService.isAdmin()) {

    return true;

  }

  alert('Access Denied');

  router.navigate([authService.getHomeRoute()]);

  return false;

};

export const facultyGuard: CanActivateFn = () => {

  const router = inject(Router);

  const roleService = inject(RoleService);

  const authService = inject(AuthService);

  if (
    roleService.isAdmin() ||
    roleService.isFaculty()
  ) {

    return true;

  }

  alert('Access Denied');

  router.navigate([authService.getHomeRoute()]);

  return false;

};

export const studentGuard: CanActivateFn = () => {

  const router = inject(Router);

  const roleService = inject(RoleService);

  const authService = inject(AuthService);

  if (
    roleService.isAdmin() ||
    roleService.isFaculty() ||
    roleService.isStudent()
  ) {

    return true;

  }

  alert('Access Denied');

  router.navigate([authService.getHomeRoute()]);

  return false;

};