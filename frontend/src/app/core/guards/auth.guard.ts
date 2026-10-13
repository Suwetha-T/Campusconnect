import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = () => {

  const router = inject(Router);
  const authService = inject(AuthService);

  if (authService.isLoggedIn()) {

    return true;

  }

  router.navigate(['/login']);

  return false;

};

export const guestGuard: CanActivateFn = () => {

  const router = inject(Router);
  const authService = inject(AuthService);

  if (!authService.isLoggedIn()) {

    return true;

  }

  router.navigate([authService.getHomeRoute()]);

  return false;

};
