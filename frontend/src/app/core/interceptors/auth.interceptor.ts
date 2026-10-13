import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

import { AuthService } from '../services/auth.service';
export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const router = inject(Router);
  const authService = inject(AuthService);

  const token = authService.getToken();

  let authReq = req;

  if (token) {

    authReq = req.clone({

      setHeaders: {

        Authorization: `Bearer ${token}`

      }

    });

  }

  return next(authReq).pipe(

    catchError(error => {

      let errorMsg = 'An unexpected error occurred.';
      if (error.error && error.error.message) {
         errorMsg = error.error.message;
      } else if (error.error && error.error.messages) {
         // for validation errors — show field name + message
         const msgs = error.error.messages;
         errorMsg = Object.entries(msgs)
           .map(([field, msg]) => {
             const label = field.replace(/([A-Z])/g, ' $1').replace(/^./, s => s.toUpperCase());
             return `${label}: ${msg}`;
           })
           .join('\n');
      }

      if (error.status === 401) {
        alert('Session expired or unauthorized. Please login again.');
        authService.logout();
        router.navigate(['/login']);
      } else if (error.status === 403) {
        alert('Forbidden: You do not have permission to access this resource.');
        authService.logout();
        router.navigate(['/login']);
      } else {
        alert('Error: ' + errorMsg);
      }

      return throwError(() => error);

    })

  );

};