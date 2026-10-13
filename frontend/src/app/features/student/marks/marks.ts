import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MarksModel } from '../../../core/models/marks';
import { MarksService } from '../../../core/services/marks.service';

@Component({
  selector: 'app-marks',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './marks.html',
  styleUrl: './marks.css'
})
export class Marks implements OnInit {

  marksList: MarksModel[] = [];
  private marksService = inject(MarksService);

  ngOnInit(): void {
    this.marksService.getCurrentStudentMarks().subscribe({
      next: (data) => {
        this.marksList = data;
      },
      error: (err) => console.error(err)
    });
  }

}
