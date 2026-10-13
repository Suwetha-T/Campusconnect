import { Injectable, inject } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { DashboardModel } from '../models/dashboard';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private http = inject(HttpClient);

  private apiUrl = `${environment.apiUrl}/dashboard`;

  getDashboardData(): Observable<DashboardModel> {

    return this.http.get<DashboardModel>(this.apiUrl);

  }

}