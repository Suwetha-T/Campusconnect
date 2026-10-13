import { Injectable, inject } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { AnnouncementModel } from '../models/announcement';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AnnouncementService {

  private http = inject(HttpClient);

  private apiUrl = `${environment.apiUrl}/announcements`;

  getAllAnnouncements(): Observable<AnnouncementModel[]> {

    return this.http.get<AnnouncementModel[]>(this.apiUrl);

  }

  getAnnouncementById(id: number): Observable<AnnouncementModel> {

    return this.http.get<AnnouncementModel>(
      `${this.apiUrl}/${id}`
    );

  }

  addAnnouncement(
    announcement: AnnouncementModel
  ): Observable<AnnouncementModel> {

    return this.http.post<AnnouncementModel>(
      this.apiUrl,
      announcement
    );

  }

  updateAnnouncement(
    id: number,
    announcement: AnnouncementModel
  ): Observable<AnnouncementModel> {

    return this.http.put<AnnouncementModel>(
      `${this.apiUrl}/${id}`,
      announcement
    );

  }

  deleteAnnouncement(
    id: number
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );

  }

}