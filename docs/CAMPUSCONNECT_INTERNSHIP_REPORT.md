# CAMPUSCONNECT — INTERNSHIP TECHNICAL REPORT

---

# CHAPTER 1: KEY LEARNINGS

This chapter presents the key concepts, core principles, and technical knowledge acquired during the internship period. The learning journey covered full-stack software development principles, enterprise Java backend engineering with Spring Boot 3, RESTful API design, database management and ORM with MySQL and Hibernate, client-side application development using Angular Standalone components, stateless authentication using JSON Web Tokens (JWT), role-based access control (RBAC), and version control using Git and GitHub.

---

## 1.1. FULL-STACK SOFTWARE ARCHITECTURE
Full-stack software development encompasses both client-side (frontend) and server-side (backend) application development. During the internship, I learned how modern enterprise applications decouple the presentation layer from the business logic layer using a RESTful API architecture. 

The three-tier architecture implemented during the project consists of:
- **Presentation Layer (Frontend)**: Angular 19 application providing interactive UI components, dynamic data binding, reactive forms, and client-side routing.
- **Application/Business Logic Layer (Backend)**: Spring Boot REST controllers, services, security filters, DTO mappers, and validation logic.
- **Data Layer (Database)**: MySQL relational database accessed via Spring Data JPA and Hibernate ORM.

This decoupled architecture ensures high scalability, maintainability, clean separation of concerns, and independence between frontend UI rendering and backend data persistence.

---

## 1.2. ENTERPRISE JAVA & SPRING BOOT 3
Spring Boot is an open-source Java-based framework used to create microservices and enterprise web applications. During the internship, I gained extensive hands-on experience developing RESTful web services using Spring Boot 3.5.3.

Key Spring Boot concepts and components learned include:
- **Dependency Injection & Inversion of Control (IoC)**: Managing bean lifecycles using `@Service`, `@Repository`, `@RestController`, `@Autowired`, and constructor injection.
- **Spring Data JPA & Hibernate**: Simplifying database operations using `JpaRepository` interfaces, custom JPQL queries, and `@Entity` lifecycle annotations (`@PrePersist`, `@PreUpdate`).
- **Data Transfer Objects (DTO) & Mappers**: Isolating database entities from API request/response payloads to prevent over-posting and secure internal domain logic.
- **Jakarta Bean Validation**: Applying data constraints using `@NotBlank`, `@NotNull`, `@Min`, `@PastOrPresent`, and custom boolean validation methods.
- **Global Exception Handling**: Intercepting domain exceptions globally using `@RestControllerAdvice` and `@ExceptionHandler` to return consistent JSON error structures.

---

## 1.3. RESTFUL API DESIGN & ARCHITECTURE
REST (Representational State Transfer) is an architectural style for designing networked applications. I learned how to design, implement, and document stateless RESTful endpoints following standard HTTP methods and status codes:
- **HTTP GET**: Fetching domain resources (e.g., `/api/v1/students`, `/api/v1/courses/me`).
- **HTTP POST**: Creating new entities or submitting credentials (e.g., `/api/v1/auth/login`, `/api/v1/attendance`).
- **HTTP PUT**: Updating existing domain records.
- **HTTP DELETE**: Removing records with proper foreign key cascade checks.
- **HTTP Status Codes**: Using `200 OK`, `201 Created`, `400 Bad Request`, `401 Unauthorized`, `403 Forbidden`, `404 Not Found`, and `409 Conflict`.

---

## 1.4. ANGULAR & STANDALONE COMPONENT FRAMEWORK
Angular is a TypeScript-based open-source web application framework developed by Google. During the internship, I learned Angular 19 utilizing **Standalone Components**, eliminating legacy `NgModule` boilerplates.

Key Angular concepts mastered include:
- **Standalone Components & Directives**: Creating modular UI components using `@Component` with direct import arrays.
- **Client-Side Routing & Guards**: Configuring `app.routes.ts` with `CanActivateFn` guards (`authGuard`, `guestGuard`, `adminGuard`, `facultyGuard`, `studentGuard`) to restrict route navigation based on authentication status and user roles.
- **HTTP Interceptors**: Implementing `authInterceptor` to automatically attach `Authorization: Bearer <token>` headers to outgoing HTTP requests and capture global HTTP error codes.
- **Reactive Forms & Data Binding**: Utilizing `FormBuilder`, `Validators`, `[(ngModel)]`, and structural control flows (`@if`, `@for`) for dynamic UI rendering.
- **Services & Dependency Injection**: Centralizing HTTP requests and authentication state management using injectable RxJS `BehaviorSubject` services.

---

## 1.5. DATABASE DESIGN & MYSQL ORM
Database management is critical for storing structured academic records. MySQL was used as the relational database management system (RDBMS) paired with Spring Data JPA.

Key database concepts learned include:
- **Relational Schema Design**: Designing tables with Primary Keys, Foreign Keys, Unique constraints, and indexed columns.
- **JPA Entity Relationships**: Configuring `@OneToOne`, `@OneToMany`, `@ManyToOne`, and `@ManyToMany` mappings with appropriate `FetchType.LAZY` and cascade settings.
- **Inheritance & MappedSuperclass**: Creating an abstract `BaseEntity` carrying `createdAt` and `updatedAt` audit fields inherited by all domain entities.

---

## 1.6. SPRING SECURITY & JWT AUTHENTICATION
Security is essential for safeguarding student academic records. I gained deep knowledge of implementing stateless JWT-based authentication and Role-Based Access Control (RBAC) in Spring Security.

Key security concepts learned include:
- **BCrypt Password Hashing**: Hashing passwords using `BCryptPasswordEncoder` prior to database persistence.
- **JWT Generation & Validation**: Issuing HMAC-SHA256 signed JWT tokens containing user claims (`sub`, `role`) and validating signature integrity on incoming requests.
- **Security Filter Chain**: Configuring stateless session management (`SessionCreationPolicy.STATELESS`), endpoint authorization rules, and CORS configuration.
- **Custom Security Entry Points**: Implementing custom `RestAuthenticationEntryPoint` (401 Unauthorized) and `RestAccessDeniedHandler` (403 Access Denied) handlers returning structured JSON error payloads.

---

## 1.7. VERSION CONTROL WITH GIT & GITHUB
Git and GitHub were utilized throughout the internship lifecycle to manage code revisions.

Key practices learned:
- Branching, committing, and clean commit history maintenance.
- Code review, pull requests, and multi-developer repository synchronization.

---

## 1.8. DEVELOPMENT TOOLS & ENVIRONMENT
The development stack consisted of industry-standard tools:
- **Backend IDE**: IntelliJ IDEA / Antigravity Environment with Java 17 SDK and Maven 3.x.
- **Frontend Environment**: Visual Studio Code with Node.js 20, Angular CLI 19, and TypeScript 5.
- **Database Tools**: MySQL Workbench and Command Line Client.
- **API Testing**: Postman for REST API collection validation.

---

# CHAPTER 2: PROJECTS

This chapter provides an exhaustive breakdown of the project development lifecycle carried out during the one-month internship. The learning progressed from environment setup and backend architecture design to entity mapping, REST API implementation, Spring Security integration, Angular UI construction, and final system deployment validation.

---

## 2.1. TASK ON FIRST WEEK: PROJECT INITIALIZATION & CORE BACKEND ARCHITECTURE
The first week focused on environment configuration, establishing database schemas, and building the foundational backend layer using Java Spring Boot 3.

### WHAT I LEARNED
- **Project Structure**: Created Spring Boot application (`com.sms.backend`) with Maven dependencies for Web, Security, Data JPA, MySQL Driver, Lombok, and Validation.
- **Base Domain Modeling**: Built abstract `@MappedSuperclass` `BaseEntity` providing automated `createdAt` and `updatedAt` timestamp auditing via `@PrePersist` and `@PreUpdate`.
- **Enumeration Design**: Defined system enums (`Role`, `Gender`, `ExamType`, `AttendanceStatus`, `EnrollmentStatus`, `AnnouncementTarget`).
- **Database Schema**: Configured MySQL database `campusconnect_db` in `application.properties`. Built JPA entities (`User`, `Department`, `Faculty`, `Student`, `Course`).

### CONCLUSION
Week 1 provided a solid foundation in domain modeling and relational entity mapping in Spring Boot.

---

## 2.2. TASK ON SECOND WEEK: REPOSITORY, SERVICE & REST API IMPLEMENTATION
During the second week, I developed the complete backend service layer, data transfer objects (DTOs), mapper utilities, and REST controllers.

### WHAT I LEARNED
- **Repository Interfaces**: Created Spring Data `JpaRepository` interfaces with derived finder queries (`findByEmail`, `findByRollNumber`, `findByDepartmentId`).
- **DTO & Mapper Pattern**: Created request and response DTO classes with Jakarta Validation annotations (`@NotBlank`, `@Min(0)`), paired with static mapper utilities (`StudentMapper`, `FacultyMapper`, etc.).
- **Service Layer**: Built interface-driven service implementations (`StudentServiceImpl`, `CourseServiceImpl`, `MarksServiceImpl`) wrapping database operations in transactional boundaries.
- **Controller & Exception Handling**: Exposed REST controllers (`/api/v1/students`, `/api/v1/courses`, `/api/v1/marks`) and created `GlobalExceptionHandler` mapping exceptions to 400/404/409 HTTP status codes.

### CONCLUSION
The second week completed all fundamental CRUD APIs and established robust backend validation and error handling.

---

## 2.3. TASK ON THIRD WEEK: SPRING SECURITY, JWT & ANGULAR FRONTEND SCENARIOS
The third week focused on securing the backend APIs with JWT tokens and building the core Angular client infrastructure.

### WHAT I LEARNED
- **JWT Engine**: Implemented `JwtService` using the JJWT library for token generation, signature validation, and claim parsing.
- **Spring Security Integration**: Configured `SecurityConfig`, `JwtAuthenticationFilter`, `RestAuthenticationEntryPoint`, and `RestAccessDeniedHandler`.
- **Angular Architecture**: Initialized Angular standalone application with Bootstrap 5. Created TypeScript models matching backend DTOs.
- **Auth & Route Guards**: Implemented Angular `AuthService`, `authInterceptor`, and functional guards (`authGuard`, `adminGuard`, `facultyGuard`, `studentGuard`).

### CONCLUSION
Week 3 established an end-to-end secure communication bridge between the Angular SPA and Spring Boot backend.

---

## 2.4. TASK ON WEEK FOUR: CAMPUSCONNECT FULL-STACK SYSTEM IMPLEMENTATION

### INTRODUCTION
In week four, I completed the full-stack **CampusConnect** Student Management System. CampusConnect is an enterprise web application designed to automate and manage institutional operations for Administrators, Faculty, and Students. It features complete authentication, role-based access control, modular CRUD management, student self-service portals, and dynamic role-aware dashboard analytics.

### OBJECTIVES
1. Design and build a scalable RESTful backend architecture using Spring Boot 3 and MySQL.
2. Implement stateless JWT authentication with BCrypt password encryption and Spring Security RBAC.
3. Build a modern, responsive Angular 19 single-page application using Standalone Components and Bootstrap 5.
4. Provide complete management workflows for Users, Departments, Faculty, Students, Courses, Attendance, Marks, and Announcements.
5. Provide dedicated self-service views (`/me`) for Students and Faculty.
6. Provide role-aware dashboard summary cards and notice boards.

---

### EXISTING SYSTEM VS. PROPOSED SYSTEM

#### Existing System (Manual / Fragmented Spreadsheets)
Traditional campus management relies on manual paper registers or isolated Excel spreadsheets:
- Data fragmentation leading to inconsistent records across departments.
- High risk of data loss and manual entry errors.
- Lack of role-based security; sensitive student marks accessible without audit trails.
- Slow manual calculation of attendance percentages and GPA marks.
- No central broadcast platform for institutional announcements.

#### Proposed System (CampusConnect Full-Stack System)
The proposed CampusConnect system replaces manual methods with a unified, cloud-ready RESTful architecture with role-based security:
- Centralized MySQL database ensuring transactional data consistency.
- Granular Role-Based Access Control (`ADMIN`, `FACULTY`, `STUDENT`).
- Automated real-time metric aggregation on dynamic dashboards.
- Self-service web access for students to monitor attendance and marks instantly.
- High security using BCrypt password hashing and JWT token authorization.

| Parameter | Existing (Manual / Legacy System) | Proposed (CampusConnect AI & Web System) |
|---|---|---|
| **Data Storage** | Paper registers / Fragmented Excel | Centralized MySQL Database with JPA |
| **Authentication** | None / Physical Signatures | Stateless JWT + BCrypt Password Encryption |
| **Access Control** | Manual oversight | Role-Based Access Control (RBAC) |
| **Data Consistency** | Prone to duplicates & errors | Relational Integrity & JPA Constraints |
| **Student Access** | Notice board physical visits | Self-Service Web Portal (`/student/profile`, `/me`) |
| **Dashboard Analytics**| Manual end-of-term calculations | Real-time dynamic dashboard metrics |
| **Scalability** | Low / Labor intensive | High / Modular REST micro-architecture |

*Table 2.1: Comparison between Existing and Proposed Systems*

---

### SYSTEM ARCHITECTURE & WORKFLOW

```
+-------------------------------------------------------------------------+
|                      ANGULAR 19 FRONTEND (SPA)                          |
|  [ Login / Admin Layout / Student Portal / Role Guards / Interceptors ]  |
+------------------------------------+------------------------------------+
                                     |
                                (HTTP / REST)
                                     |
                                     v
+-------------------------------------------------------------------------+
|                      SPRING BOOT 3 BACKEND API                          |
|                                                                         |
|  +-------------------------------------------------------------------+  |
|  |                     SECURITY & JWT LAYER                          |  |
|  | [ JwtAuthenticationFilter | SecurityConfig | RestAuthEntryPoint ] |  |
|  +---------------------------------+---------------------------------+  |
|                                    |                                    |
|                                    v                                    |
|  +-------------------------------------------------------------------+  |
|  |                     REST CONTROLLER LAYER                         |  |
|  | [ AuthController | StudentController | DashboardController ... ]  |  |
|  +---------------------------------+---------------------------------+  |
|                                    |                                    |
|                                    v                                    |
|  +-------------------------------------------------------------------+  |
|  |                      SERVICE & MAPPER LAYER                       |  |
|  | [ StudentServiceImpl | CourseServiceImpl | DTO Mappers ]          |  |
|  +---------------------------------+---------------------------------+  |
|                                    |                                    |
|                                    v                                    |
|  +-------------------------------------------------------------------+  |
|  |                      SPRING DATA JPA LAYER                        |  |
|  | [ StudentRepository | CourseRepository | UserRepository ... ]     |  |
|  +---------------------------------+---------------------------------+  |
+------------------------------------+------------------------------------+
                                     |
                                (JDBC / ORM)
                                     |
                                     v
+-------------------------------------------------------------------------+
|                         MYSQL 8 DATABASE                                |
|   [ users | departments | faculties | students | courses | marks ]      |
+-------------------------------------------------------------------------+
```
*Figure 2.1: CampusConnect System Architecture & Layered Workflow Diagram*

---

### MODULE DESCRIPTIONS

1. **Authentication Module**: Manages user registration, BCrypt password validation, JWT token issuance, and security context initialization.
2. **User Management Module**: Allows Administrators to manage system users and assign roles (`ADMIN`, `FACULTY`, `STUDENT`).
3. **Department Module**: Controls academic departments (`code`, `name`) serving as parent relationships for faculty, students, and courses.
4. **Faculty Module**: Manages teaching staff records mapped to user credentials and assigned departments.
5. **Student Module**: Manages enrolled student profiles (`rollNumber`, `dob`, `gender`, `department`), including student self-service profile APIs.
6. **Course Module**: Catalogues academic courses, credit weightages, semesters, and instructor assignments.
7. **Student-Course Enrollment Module**: Handles student course enrollments and status tracking.
8. **Attendance Module**: Records daily attendance status (`PRESENT`, `ABSENT`, `LATE`, `EXCUSED`) with course-wise student percentages.
9. **Marks Module**: Logs examination results (`MID_TERM`, `FINAL`, `QUIZ`) with automated `marksObtained` <= `maxMarks` validation.
10. **Announcement Module**: System-wide broadcast system allowing Admins to publish notice cards targeted to specific user roles.
11. **Dashboard Module**: Real-time aggregate metric engine presenting customized summary cards based on the authenticated user role.

---

### METHODOLOGY & IMPLEMENTATION STEPS

1. **Database Design & ER Mapping**: Configured MySQL tables with strict relational foreign keys and JPA entity bindings.
2. **DTO & Mapper Construction**: Separated domain models from presentation layers using dedicated request/response DTOs and static mapping methods.
3. **Service Layer Development**: Implemented transactional `@Service` interfaces containing core business logic, duplicate checks, and resource existence validations.
4. **Security Filter Pipeline**: Wired `JwtAuthenticationFilter` ahead of Spring's `UsernamePasswordAuthenticationToken` filter in `SecurityConfig`.
5. **Angular Single-Page Architecture**: Created Standalone Angular components, configured route guards (`adminGuard`, `studentGuard`), and centralized HTTP authorization headers in `authInterceptor`.
6. **API Integration & UI Polish**: Embedded Bootstrap 5 cards, tables, modal dialogs, and responsive navigation sidebars connected to backend endpoints.

---

### TOOLS AND TECHNOLOGIES USED

- **Java 17**: Core backend programming language offering high performance and modern language features.
- **Spring Boot 3.5.3**: Backend application framework managing DI, REST APIs, and transactional ORM.
- **Spring Security 6 & JJWT**: Security framework providing stateless authentication and role authorization.
- **MySQL 8**: Enterprise relational database storing academic records.
- **Angular 19**: Client-side TypeScript SPA framework utilizing Standalone components.
- **Bootstrap 5 & Vanilla CSS**: UI framework providing responsive grids, modals, cards, and custom CSS styling.
- **Maven**: Dependency management and build tool for Java backend artifacts.
- **Git & GitHub**: Distributed version control system for project collaboration.

---

### OUTPUT ANALYSIS & SCREENSHOT PLAN

#### 1. Login Page
Provides secure user authentication, allowing authorized users to access CampusConnect with role-based redirection.
*Figure 2.1: Login Page of the Application*

#### 2. Dashboard Page
Displays dynamic metric summary cards (Total Students, Faculty, Courses, Departments) and targeted announcement boards tailored to the logged-in user role.
*Figure 2.2: Dynamic Role-Aware Dashboard Page*

#### 3. Student Management Interface
Provides complete student record listings, roll number search filters, department assignments, and modal dialogs for adding/editing student details.
*Figure 2.3: Student Management Interface*

#### 4. Faculty & Department Management Pages
Displays academic department structures and faculty employee profiles linked with user credentials.
*Figure 2.4: Department & Faculty Management Interface*

#### 5. Course & Student-Course Mapping Interface
Manages course subject catalogues, credit distribution, and student course registration lists.
*Figure 2.5: Course & Enrollment Management Interface*

#### 6. Attendance & Marks Management Pages
Allows faculty/admin to log daily attendance statuses and record exam marks with dynamic range validation.
*Figure 2.6: Attendance & Marks Management Interface*

#### 7. Announcement Board Interface
Allows Admins to post notices targeted to all users, students only, or faculty only.
*Figure 2.7: Announcement Board Interface*

#### 8. Student Self-Service Portal
Enables logged-in students to view their personal profile details, enrolled courses, attendance records, and exam results.
*Figure 2.8: Student Self-Service Portal Interface*

---

### CONCLUSION
This report detailed the complete architectural setup, design methodology, module descriptions, security framework, database models, and user workflows of the **CampusConnect** Student Management System. By combining Java Spring Boot 3, MySQL, Spring Security JWT, and Angular 19 Standalone components, CampusConnect provides an enterprise full-stack platform for modern academic administration. The application successfully replaces manual paper-based processes with automated RESTful web services, role-based data isolation, real-time analytics dashboards, and responsive web user interfaces.
