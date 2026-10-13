import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentCourseModel } from '../../../core/models/student-course';
import { StudentCourseService } from '../../../core/services/student-course.service';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './courses.html',
  styleUrl: './courses.css'
})
export class Courses implements OnInit {

  coursesList: StudentCourseModel[] = [];
  private studentCourseService = inject(StudentCourseService);

  ngOnInit(): void {
    this.studentCourseService.getCurrentStudentCourses().subscribe({
      next: (data) => {
        this.coursesList = data;
      },
      error: (err) => console.error(err)
    });
  }

}
