import { Injectable, inject } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { MarksModel } from '../models/marks';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MarksService {

  private http = inject(HttpClient);

  private apiUrl = `${environment.apiUrl}/marks`;

  getAllMarks(): Observable<MarksModel[]> {

    return this.http.get<MarksModel[]>(this.apiUrl);

  }

  addMarks(marks: MarksModel): Observable<MarksModel> {

    return this.http.post<MarksModel>(this.apiUrl, marks);

  }

  getCurrentStudentMarks(): Observable<MarksModel[]> {

    return this.http.get<MarksModel[]>(
      `${this.apiUrl}/me`
    );

  }

}