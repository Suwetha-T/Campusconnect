import { Routes } from '@angular/router';

import { Login } from './features/auth/login/login';

import { Dashboard } from './features/admin/dashboard/dashboard';

import { Department } from './features/admin/department/department';

import { Faculty } from './features/admin/faculty/faculty';

import { Student } from './features/admin/student/student';

import { Course } from './features/admin/course/course';

import { Attendance } from './features/admin/attendance/attendance';

import { Marks } from './features/admin/marks/marks';

import { Announcement } from './features/admin/announcement/announcement';

import { Users } from './features/admin/users/users';

import { AdminLayout } from './layouts/admin-layout/admin-layout';

import { authGuard, guestGuard } from './core/guards/auth.guard';

import {
  adminGuard,
  facultyGuard,
  studentGuard
} from './core/guards/role.guard';

import { StudentCourse } from './features/admin/student-course/student-course';
import { Profile as StudentProfile } from './features/student/profile/profile';
import { Attendance as StudentAttendance } from './features/student/attendance/attendance';
import { Marks as StudentMarks } from './features/student/marks/marks';
import { Courses as StudentCourses } from './features/student/courses/courses';

export const routes: Routes = [

  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  {
    path: 'login',
    component: Login,
    canActivate: [guestGuard]
  },

  {
    path: 'admin',
    component: AdminLayout,
    canActivate: [authGuard, adminGuard],

    children: [
      {
        path: '',
        component: Dashboard
      },
      {
        path: 'departments',
        component: Department
      },
      {
        path: 'faculty',
        component: Faculty
      },
      {
        path: 'students',
        component: Student
      },
      {
        path: 'courses',
        component: Course
      },
      {
        path: 'student-courses',
        component: StudentCourse
      },
      {
        path: 'attendance',
        component: Attendance
      },
      {
        path: 'marks',
        component: Marks
      },
      {
        path: 'announcements',
        component: Announcement
      },
      {
        path: 'users',
        component: Users
      }
    ]
  },

  {
    path: 'faculty',
    component: AdminLayout,
    canActivate: [authGuard, facultyGuard],

    children: [
      {
        path: '',
        component: Dashboard
      },
      {
        path: 'students',
        component: Student
      },
      {
        path: 'attendance',
        component: Attendance
      },
      {
        path: 'marks',
        component: Marks
      },
      {
        path: 'announcements',
        component: Announcement
      }
    ]
  },

  {
    path: 'student',
    component: AdminLayout,
    canActivate: [authGuard, studentGuard],

    children: [
      {
        path: '',
        component: Dashboard
      },
      {
        path: 'profile',
        component: StudentProfile
      },
      {
        path: 'attendance',
        component: StudentAttendance
      },
      {
        path: 'marks',
        component: StudentMarks
      },
      {
        path: 'courses',
        component: StudentCourses
      },
      {
        path: 'announcements',
        component: Announcement
      }
    ]
  },

  {
    path: '**',
    redirectTo: 'login'
  }

];