# Student Management System - Project Context

## Project Overview

I am developing a professional Student Management System.

Goal:
Complete Version 1 before deadline.

The application should support multiple users with different privileges.

---

# Technology Stack

## Backend

Framework:
Spring Boot 3.x

Language:
Java

Database:
MySQL

Build Tool:
Maven

Architecture:
REST API based

Security:
Spring Security + JWT

---

## Frontend

Framework:
Angular

Communication:
HTTP Client calling Spring Boot REST APIs

---

# Existing Project Structure

Backend package:

com.sms.backend

Current layers:

controller
dto
entity
enums
exception
mapper
repository
security
service
service.impl
util


Frontend:

Angular application with:
components
services
guards
interceptors
models


---

# Existing Completed Work

The following parts are already implemented:

- Spring Boot project setup
- Maven configuration
- MySQL connection
- Entity structure
- Repository layer
- Basic REST APIs
- Angular project setup
- Basic UI pages


Existing modules:

- User
- Department
- Faculty
- Student
- Course
- StudentCourse
- Attendance
- Marks
- Announcement


---

# User Roles

The system has three roles:

ADMIN

FACULTY

STUDENT


---

# Required Permissions


## ADMIN

Full system access:

- Manage students
- Manage faculty
- Manage departments
- Manage courses
- Manage attendance
- Manage marks
- Manage announcements


## FACULTY

Access:

- View assigned students
- Manage attendance
- Manage marks
- View announcements


## STUDENT

Access:

- View own profile
- View own attendance
- View own marks
- View courses
- View announcements


---

# Remaining Version 1 Work


Priority order:

1. Authentication

Need:

- Login
- JWT generation
- JWT validation
- Logout
- Token handling
- Password encryption
- Authentication failure handling


2. Role Based Access Control

Need:

Backend:
- Spring Security configuration
- Role authorization
- Protected APIs


Frontend:
- Auth service
- JWT interceptor
- Route guards
- Role guards
- Hide unauthorized menus


3. Dashboards


ADMIN Dashboard:

Cards:

- Total Students
- Total Faculty
- Total Departments
- Total Courses
- Recent announcements


FACULTY Dashboard:

- Assigned courses
- Pending attendance
- Announcements


STUDENT Dashboard:

- Attendance percentage
- Latest marks
- Courses
- Announcements


4. Navigation

Need:

- Sidebar
- Navbar
- Profile
- Logout
- Active menu


5. Validation

Need:

- Required fields
- Invalid marks prevention
- Date validation
- Error messages


6. Error Handling

Need:

- Global API error handling
- 401 handling
- 403 handling
- User friendly messages


7. Responsive UI

Support:

Desktop
Laptop
Tablet


---

# Important Development Rules

Follow these rules strictly:

1. Do not rebuild existing modules.

2. Do not create duplicate entities.

3. Before changing files:
   - Analyze current implementation.
   - Explain what will change.

4. Make changes incrementally.

5. After every major change:
   - Run backend compilation.
   - Run frontend build.
   - Fix errors.

6. Maintain existing package structure.

7. Avoid unnecessary dependencies.

8. Write clean production-style code.

9. If something is unclear, inspect existing code before deciding.

10. Never remove working functionality.


---

# Current Development Strategy

Complete Version 1 first.

Do not implement Version 2 modules:

- Timetable
- Hostel
- Library
- Transport
- Fees
- Reports
- Parent portal


Focus only on:

Authentication
Authorization
RBAC
Dashboard
Navigation
Validation
Error Handling
Testing
