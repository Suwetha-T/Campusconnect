"""
CampusConnect - Final Internship Report DOCX Generator
Builds a properly formatted DOCX with all screenshots embedded
matching the sample_report.docx structure.
"""
import zipfile, shutil, os, re
import xml.etree.ElementTree as ET

SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
SCREENSHOTS_DIR = os.path.join(SCRIPT_DIR, "screenshots")
SAMPLE_DOCX = os.path.join(os.path.dirname(SCRIPT_DIR), "sample_report.docx")
OUTPUT_DOCX = os.path.join(SCRIPT_DIR, "CampusConnect_Internship_Report_Final.docx")

# ── namespace shortcuts ──────────────────────────────────────────────────────
W  = "http://schemas.openxmlformats.org/wordprocessingml/2006/main"
R  = "http://schemas.openxmlformats.org/officeDocument/2006/relationships"
RI = "http://schemas.openxmlformats.org/package/2006/relationships"
WP = "http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing"
A  = "http://schemas.openxmlformats.org/drawingml/2006/main"
PIC= "http://schemas.openxmlformats.org/drawingml/2006/picture"

for ns, uri in [("w",W),("r",R),("wp",WP),("a",A),("pic",PIC)]:
    ET.register_namespace(ns, uri)

# ── helpers ──────────────────────────────────────────────────────────────────
def w(tag):  return f"{{{W}}}{tag}"
def rns(tag): return f"{{{R}}}{tag}"

def para(text, bold=False, size=None, align=None, heading=None):
    p  = ET.Element(w("p"))
    pPr= ET.SubElement(p, w("pPr"))
    if heading:
        ET.SubElement(pPr, w("pStyle")).set(w("val"), heading)
    if align:
        ET.SubElement(pPr, w("jc")).set(w("val"), align)
    if text:
        run = ET.SubElement(p, w("r"))
        rPr = ET.SubElement(run, w("rPr"))
        if bold:
            ET.SubElement(rPr, w("b"))
        if size:
            ET.SubElement(rPr, w("sz")).set(w("val"), str(size))
            ET.SubElement(rPr, w("szCs")).set(w("val"), str(size))
        t = ET.SubElement(run, w("t"))
        t.text = text
        t.set("{http://www.w3.org/XML/1998/namespace}space", "preserve")
    return p

def page_break():
    p  = ET.Element(w("p"))
    run= ET.SubElement(p, w("r"))
    br = ET.SubElement(run, w("br"))
    br.set(w("type"), "page")
    return p

def image_para(rId, width_emu=5580000, height_emu=3500000, caption=""):
    """Inline image paragraph."""
    p   = ET.Element(w("p"))
    pPr = ET.SubElement(p, w("pPr"))
    ET.SubElement(pPr, w("jc")).set(w("val"), "center")
    run = ET.SubElement(p, w("r"))
    drw = ET.SubElement(run, f"{{{W}}}drawing")
    inline = ET.SubElement(drw, f"{{{WP}}}inline")
    inline.set("distT","0"); inline.set("distB","0")
    inline.set("distL","0"); inline.set("distR","0")
    ext = ET.SubElement(inline, f"{{{WP}}}extent")
    ext.set("cx", str(width_emu)); ext.set("cy", str(height_emu))
    ET.SubElement(inline, f"{{{WP}}}effectExtent",
                  attrib={"l":"0","t":"0","r":"0","b":"0"})
    ET.SubElement(inline, f"{{{WP}}}docPr",
                  attrib={"id":"1","name":caption,"descr":caption})
    ET.SubElement(inline, f"{{{WP}}}cNvGraphicFramePr")
    grfr = ET.SubElement(inline,
        "{http://schemas.openxmlformats.org/drawingml/2006/main}graphic")
    grfd = ET.SubElement(grfr,
        "{http://schemas.openxmlformats.org/drawingml/2006/main}graphicData")
    grfd.set("uri","http://schemas.openxmlformats.org/drawingml/2006/picture")
    picEl= ET.SubElement(grfd,
        f"{{{PIC}}}pic")
    nvPicPr= ET.SubElement(picEl, f"{{{PIC}}}nvPicPr")
    ET.SubElement(nvPicPr, f"{{{PIC}}}cNvPr",
                  attrib={"id":"0","name":caption})
    ET.SubElement(nvPicPr, f"{{{PIC}}}cNvPicPr")
    blipFill = ET.SubElement(picEl, f"{{{PIC}}}blipFill")
    blip = ET.SubElement(blipFill,
        "{http://schemas.openxmlformats.org/drawingml/2006/main}blip")
    blip.set(f"{{{R}}}embed", rId)
    ET.SubElement(blipFill,
        "{http://schemas.openxmlformats.org/drawingml/2006/main}stretch")
    spPr = ET.SubElement(picEl, f"{{{PIC}}}spPr")
    xfrm = ET.SubElement(spPr,
        "{http://schemas.openxmlformats.org/drawingml/2006/main}xfrm")
    off  = ET.SubElement(xfrm,
        "{http://schemas.openxmlformats.org/drawingml/2006/main}off")
    off.set("x","0"); off.set("y","0")
    ext2 = ET.SubElement(xfrm,
        "{http://schemas.openxmlformats.org/drawingml/2006/main}ext")
    ext2.set("cx",str(width_emu)); ext2.set("cy",str(height_emu))
    prstGeom = ET.SubElement(spPr,
        "{http://schemas.openxmlformats.org/drawingml/2006/main}prstGeom")
    prstGeom.set("prst","rect")
    ET.SubElement(prstGeom,
        "{http://schemas.openxmlformats.org/drawingml/2006/main}avLst")
    # caption para
    capPara = ET.Element(w("p"))
    cPr = ET.SubElement(capPara, w("pPr"))
    ET.SubElement(cPr, w("jc")).set(w("val"), "center")
    cRun = ET.SubElement(capPara, w("r"))
    cRPr = ET.SubElement(cRun, w("rPr"))
    ET.SubElement(cRPr, w("i"))
    ET.SubElement(cRPr, w("sz")).set(w("val"),"18")
    t = ET.SubElement(cRun, w("t"))
    t.text = caption
    return [p, capPara]

# ── Screenshot metadata: (filename, caption, week) ──────────────────────────
SCREENSHOTS = [
    # WEEK 1
    ("01_login_page.png",
     "Figure 2.1: CampusConnect Login Page - Secure Authentication Interface", 1),
    ("01b_login_form_filled.png",
     "Figure 2.2: Login Form with Credentials - Email and Password Input", 1),

    # WEEK 2
    ("02_admin_dashboard.png",
     "Figure 2.3: Admin Dashboard - Aggregate Statistics Overview", 2),
    ("03_department_management.png",
     "Figure 2.4: Department Management - CRUD Interface for Academic Departments", 2),
    ("04_faculty_management.png",
     "Figure 2.5: Faculty Management - Teaching Staff Records and Assignments", 2),
    ("05_student_management.png",
     "Figure 2.6: Student Management - Enrolled Student Directory and Profiles", 2),
    ("06_course_management.png",
     "Figure 2.7: Course Management - Academic Course Catalog and Credit Details", 2),

    # WEEK 3
    ("07_student_course_enrollment.png",
     "Figure 2.8: Student-Course Enrollment - Course Registration Management", 3),
    ("08_attendance_management.png",
     "Figure 2.9: Attendance Management - Daily Attendance Logging Interface", 3),
    ("09_marks_management.png",
     "Figure 2.10: Marks Management - Examination Results Entry Interface", 3),
    ("10_announcements.png",
     "Figure 2.11: Announcement Board - System-wide Notice Broadcasting", 3),
    ("11_users_management.png",
     "Figure 2.12: User Management - System User Accounts and Role Assignment", 3),

    # WEEK 4
    ("12_logout_login_page.png",
     "Figure 2.13: Login Page - Role-Based Redirect After Logout", 4),
    ("13_student_dashboard.png",
     "Figure 2.14: Student Dashboard - Personalized Academic Summary", 4),
    ("14_student_profile.png",
     "Figure 2.15: Student Profile - Self-Service Personal Information View", 4),
    ("15_student_courses.png",
     "Figure 2.16: Student Enrolled Courses - Course Registration View", 4),
    ("16_student_attendance.png",
     "Figure 2.17: Student Attendance View - Personal Attendance History", 4),
    ("17_student_marks.png",
     "Figure 2.18: Student Marks View - Examination Results and Grades", 4),
    ("18_faculty_dashboard.png",
     "Figure 2.19: Faculty Dashboard - Assigned Courses and Teaching Overview", 4),
]

# ── Build the document XML body ──────────────────────────────────────────────
body = ET.Element(w("body"))

def add(el):
    if isinstance(el, list):
        for e in el: body.append(e)
    else:
        body.append(el)

# ── COVER / TITLE ────────────────────────────────────────────────────────────
for _ in range(6): add(para(""))
add(para("CAMPUSCONNECT", bold=True, size=36, align="center"))
add(para("Student Management System", bold=True, size=28, align="center"))
add(para(""))
add(para("INTERNSHIP TECHNICAL REPORT", bold=True, size=24, align="center"))
add(para(""))
add(para("Full Stack Development Internship", size=22, align="center"))
add(para(""))
for _ in range(4): add(para(""))
add(para("Technology Stack: Java Spring Boot 3 | Angular 19 | MySQL 8 | JWT Security", size=20, align="center"))
add(para("Version 1.0  |  2026", size=20, align="center"))
add(page_break())

# ── CHAPTER 1 ────────────────────────────────────────────────────────────────
add(para("CHAPTER 1", bold=True, size=28))
add(para("1. KEY LEARNINGS", bold=True, size=26))
add(para(""))
add(para(
    "This chapter presents the key concepts, core principles, and technical knowledge acquired "
    "during the internship period. The learning journey covered full-stack software development "
    "principles, enterprise Java backend engineering with Spring Boot 3, RESTful API design, "
    "database management with MySQL and Hibernate ORM, client-side application development using "
    "Angular 19 Standalone Components, stateless authentication using JSON Web Tokens (JWT), "
    "Role-Based Access Control (RBAC), and version control using Git and GitHub."
))
add(para(""))

sections = [
    ("1.1. FULL-STACK SOFTWARE ARCHITECTURE",
     "Full-stack software development encompasses both client-side (frontend) and server-side (backend) "
     "application development. During the internship, I learned how modern enterprise applications "
     "decouple the presentation layer from business logic using a RESTful API architecture. The three-tier "
     "architecture consists of: (1) Presentation Layer — Angular 19 SPA with dynamic data binding and "
     "reactive forms. (2) Business Logic Layer — Spring Boot REST controllers, services, DTO mappers, "
     "and validation logic. (3) Data Layer — MySQL relational database accessed via Spring Data JPA and Hibernate."),
    ("1.2. ENTERPRISE JAVA & SPRING BOOT 3",
     "Spring Boot is an open-source Java-based framework for creating production-ready web services. "
     "Key concepts learned include Dependency Injection, Spring Data JPA, Hibernate ORM, DTO pattern, "
     "Jakarta Bean Validation (@NotBlank, @Min, @PastOrPresent), and Global Exception Handling via "
     "@RestControllerAdvice. The backend base package is com.sms.backend running on Spring Boot 3.5.3 "
     "with Java 17 and Apache Tomcat 10."),
    ("1.3. RESTFUL API DESIGN & ARCHITECTURE",
     "REST (Representational State Transfer) is an architectural style for networked applications. "
     "I designed stateless endpoints using HTTP GET (fetch), POST (create), PUT (update), and DELETE "
     "(remove). Standard HTTP status codes used: 200 OK, 201 Created, 400 Bad Request, 401 Unauthorized, "
     "403 Forbidden, 404 Not Found, 409 Conflict. The backend exposes 40+ REST endpoints under /api/v1/."),
    ("1.4. ANGULAR 19 & STANDALONE COMPONENTS",
     "Angular is a TypeScript-based SPA framework. I used Angular 19 Standalone Components (no NgModule). "
     "Key concepts: Client-Side Routing (app.routes.ts), Functional Guards (authGuard, adminGuard, "
     "studentGuard), HTTP Interceptors (authInterceptor for JWT Bearer headers), Reactive Forms with "
     "Validators, and RxJS BehaviorSubject for auth state management. Bootstrap 5 provides responsive grid, "
     "cards, modals, and table components."),
    ("1.5. MYSQL DATABASE & JPA ORM",
     "MySQL 8 serves as the relational RDBMS. Spring Data JPA with Hibernate ORM maps Java entities to "
     "database tables. The abstract BaseEntity class provides createdAt and updatedAt audit timestamps "
     "via @PrePersist and @PreUpdate lifecycle hooks. Nine database tables: users, departments, faculties, "
     "students, courses, student_courses, attendance, marks, announcements."),
    ("1.6. SPRING SECURITY & JWT AUTHENTICATION",
     "Security is enforced via Spring Security 6 with stateless JWT authentication. BCryptPasswordEncoder "
     "hashes all passwords before persistence. JwtService uses JJWT library to issue and validate HMAC-SHA256 "
     "signed tokens. JwtAuthenticationFilter intercepts all requests, validates the Bearer token, and sets the "
     "SecurityContext. RestAuthenticationEntryPoint returns JSON 401 responses. RestAccessDeniedHandler "
     "returns JSON 403 responses."),
    ("1.7. GIT & GITHUB VERSION CONTROL",
     "Git and GitHub were used throughout the internship for code versioning, commit history management, "
     "and project collaboration. Key practices include branching strategies, meaningful commit messages, "
     "and maintaining a clean repository structure."),
    ("1.8. DEVELOPMENT TOOLS & ENVIRONMENT",
     "Backend: Java 17 JDK, Maven 3.9.16, Spring Boot 3.5.3, MySQL 8. "
     "Frontend: Node.js, Angular CLI 19, TypeScript 5, Bootstrap 5. "
     "Tools: Postman for REST API testing, MySQL Workbench for database management, "
     "Git for version control, VS Code and IntelliJ IDEA as IDEs."),
]

for title, content in sections:
    add(para(title, bold=True, size=22))
    add(para(content))
    add(para(""))

add(page_break())

# ── CHAPTER 2 ────────────────────────────────────────────────────────────────
add(para("CHAPTER 2", bold=True, size=28))
add(para("2. PROJECTS", bold=True, size=26))
add(para(""))
add(para(
    "This chapter provides an exhaustive breakdown of the project development lifecycle carried out "
    "during the one-month internship. The learning progressed from environment setup and backend "
    "architecture design to entity mapping, REST API implementation, Spring Security integration, "
    "Angular UI construction, and final system deployment validation."
))
add(para(""))

# ── WEEK SECTIONS ─────────────────────────────────────────────────────────────
week_data = {
    1: {
        "title": "2.1. TASK ON FIRST WEEK",
        "subtitle": "PROJECT INITIALIZATION & CORE BACKEND ARCHITECTURE",
        "intro": (
            "The first week focused on environment configuration, establishing the development stack, "
            "configuring the MySQL database, and building the foundational backend layer using Java Spring Boot 3. "
            "The Angular frontend project was also initialized with Bootstrap 5 integration. "
            "Key JPA entities were defined: User, Department, Faculty, Student, Course, Attendance, Marks, "
            "Announcement, and StudentCourse. The abstract BaseEntity was created to handle audit timestamps."
        ),
        "learned": [
            "Spring Boot 3 Project Setup — Configured Maven pom.xml with Web, Security, JPA, MySQL, Lombok, and Validation dependencies.",
            "Database Configuration — Set up MySQL database campusconnect_db and configured spring.datasource properties.",
            "Entity Design — Created @MappedSuperclass BaseEntity with @PrePersist and @PreUpdate lifecycle hooks.",
            "Enum Design — Defined Role, Gender, ExamType, AttendanceStatus, EnrollmentStatus, AnnouncementTarget enums.",
            "JPA Relationships — Implemented @OneToOne, @ManyToOne, @OneToMany associations with FetchType.LAZY.",
            "Angular Initialization — Created Angular 19 Standalone Application with Bootstrap 5 and TypeScript models.",
        ],
        "conclusion": (
            "The first week established a complete understanding of project structure, database schema design, "
            "and Spring Boot JPA entity mapping. All domain entities were correctly mapped to MySQL tables "
            "with proper relational foreign keys."
        ),
        "screenshots": [s for s in SCREENSHOTS if s[2] == 1],
    },
    2: {
        "title": "2.2. TASK ON SECOND WEEK",
        "subtitle": "REPOSITORY, SERVICE & REST API IMPLEMENTATION",
        "intro": (
            "During the second week, I developed the complete backend service layer including Repository "
            "interfaces, Data Transfer Objects (DTOs), Mapper utility classes, Service implementations, "
            "REST Controllers, and Global Exception Handling. The CampusConnect backend now exposed fully "
            "functional CRUD APIs for all campus entities accessible via HTTP."
        ),
        "learned": [
            "Repository Interfaces — Created JpaRepository interfaces with custom finder methods (findByEmail, findByRollNumber).",
            "DTO Pattern — Built separate request DTOs (with @Valid annotations) and response DTOs for each module.",
            "Mapper Classes — Implemented static mapper utilities (StudentMapper, FacultyMapper, CourseMapper, etc.).",
            "Service Layer — Implemented transactional @Service classes containing core business logic and duplicate-check rules.",
            "REST Controllers — Exposed /api/v1/* endpoints with proper HTTP method handlers and @Valid request validation.",
            "GlobalExceptionHandler — Created @RestControllerAdvice mapping ResourceNotFoundException (404), DuplicateResourceException (409), BadRequestException (400).",
            "Admin Dashboard UI — Built Angular admin pages for Departments, Faculty, Students, Courses with Bootstrap tables and modal dialogs.",
        ],
        "conclusion": (
            "The second week completed all fundamental CRUD APIs, robust backend validation, and the core "
            "Angular admin UI pages. The Admin role could now perform complete Create, Read, Update, Delete "
            "operations across all campus entities through the web interface."
        ),
        "screenshots": [s for s in SCREENSHOTS if s[2] == 2],
    },
    3: {
        "title": "2.3. TASK ON WEEK THREE",
        "subtitle": "SPRING SECURITY, JWT AUTHENTICATION & RBAC",
        "intro": (
            "The third week focused on securing the entire backend API using Spring Security 6 with "
            "stateless JWT token authentication and Role-Based Access Control (RBAC). Three roles were "
            "enforced: ADMIN (full access), FACULTY (attendance/marks management), and STUDENT (self-service "
            "read-only access). Custom JSON 401/403 error handlers were implemented. The Angular frontend "
            "was integrated with authInterceptor and role-aware route guards."
        ),
        "learned": [
            "JWT Service — Implemented JwtService using JJWT library for HMAC-SHA256 token generation, parsing, and expiry validation.",
            "Security Filter Chain — Configured SecurityConfig with stateless session, CORS for localhost:4200, and URL-level role matchers.",
            "JwtAuthenticationFilter — Built OncePerRequestFilter extracting Bearer token and setting SecurityContextHolder.",
            "Custom Error Handlers — RestAuthenticationEntryPoint (401 JSON) and RestAccessDeniedHandler (403 JSON).",
            "@PreAuthorize — Applied method-level security on Announcement mutations (ADMIN only).",
            "/me Endpoints — Added self-service endpoints (GET /api/v1/students/me, /attendance/me, /marks/me, /courses/me).",
            "Angular Guards — Implemented authGuard, guestGuard, adminGuard, facultyGuard, studentGuard as functional CanActivateFn.",
            "authInterceptor — Attaches Authorization: Bearer <token> to all outgoing HTTP requests and intercepts 401/403 responses.",
        ],
        "conclusion": (
            "The third week established a complete, production-grade security layer. All APIs were protected "
            "with role-based URL matchers and method-level annotations. Students could only access their own "
            "data via /me endpoints. JWT tokens controlled all authenticated sessions."
        ),
        "screenshots": [s for s in SCREENSHOTS if s[2] == 3],
    },
    4: {
        "title": "2.4. TASK ON WEEK FOUR",
        "subtitle": "CAMPUSCONNECT COMPLETE SYSTEM — STUDENT PORTAL, DASHBOARDS & FINAL VALIDATION",
        "intro": (
            "In week four, I completed the full CampusConnect system by building the Student self-service "
            "portal, role-aware dashboards, sidebar navigation, and performing end-to-end validation across "
            "all three user roles. A DataSeeder class was implemented to automatically provision seed data "
            "(Admin, Faculty, Student accounts, Department, Course, and Enrollments) on application startup. "
            "Full backend mvn compile and frontend npm run build validations were completed."
        ),
        "learned": [
            "Student Self-Service Portal — Built /student/profile, /student/courses, /student/attendance, /student/marks views.",
            "Role-Aware Dashboard — Dashboard dynamically renders different metric cards based on ADMIN, FACULTY, or STUDENT role.",
            "DataSeeder — Spring Boot CommandLineRunner seeds default admin@sms.com, faculty@sms.com, student@sms.com accounts.",
            "Sidebar Navigation — Role-filtered sidebar menus hide unauthorized sections from Faculty and Student roles.",
            "Form Validation — Added @Min(0), @AssertTrue marks <= maxMarks in MarksRequest; @PastOrPresent in AttendanceRequest.",
            "Frontend Route Guards — Fixed role guard redirects; registered student and faculty routes in app.routes.ts.",
            "End-to-End Testing — Validated all three roles login flow, CRUD operations, /me self-service views, and dashboard metrics.",
        ],
        "conclusion": (
            "The fourth week completed the CampusConnect Version 1 system. All three user roles function "
            "correctly with appropriate data access boundaries. The complete backend and frontend compile "
            "and run without errors. The system is ready for institutional deployment."
        ),
        "screenshots": [s for s in SCREENSHOTS if s[2] == 4],
    },
}

# ── Comparison Table for Week 4 ───────────────────────────────────────────────
def comparison_table():
    tbl = ET.Element(w("tbl"))
    tblPr = ET.SubElement(tbl, w("tblPr"))
    ET.SubElement(tblPr, w("tblStyle")).set(w("val"), "TableGrid")
    ET.SubElement(tblPr, w("tblW")).set(w("w"), "9000")
    ET.SubElement(tblPr, w("tblW")).set(w("type"), "dxa")

    rows = [
        ("Parameter", "Existing (Manual / Legacy)", "Proposed (CampusConnect)"),
        ("Data Storage", "Paper / Fragmented Excel", "Centralized MySQL + JPA"),
        ("Authentication", "None / Physical Signatures", "Stateless JWT + BCrypt"),
        ("Access Control", "Manual oversight", "Role-Based Access Control (RBAC)"),
        ("Data Consistency", "Prone to duplicates & errors", "Relational Integrity & JPA"),
        ("Student Access", "Notice board visits", "Self-Service Web Portal (/me)"),
        ("Dashboard Analytics", "Manual term-end calculations", "Real-time dynamic metrics"),
        ("Scalability", "Low / Labor intensive", "High / Modular REST architecture"),
    ]

    for i, row in enumerate(rows):
        tr = ET.SubElement(tbl, w("tr"))
        trPr = ET.SubElement(tr, w("trPr"))
        if i == 0:
            ET.SubElement(trPr, w("tblHeader"))
        for cell_text in row:
            tc = ET.SubElement(tr, w("tc"))
            tcPr = ET.SubElement(tc, w("tcPr"))
            ET.SubElement(tcPr, w("tcW")).set(w("w"), "3000")
            p = ET.SubElement(tc, w("p"))
            run = ET.SubElement(p, w("r"))
            rPr = ET.SubElement(run, w("rPr"))
            if i == 0:
                ET.SubElement(rPr, w("b"))
            t = ET.SubElement(run, w("t"))
            t.text = cell_text
    return tbl

# ── Collect all image rIds ────────────────────────────────────────────────────
# We'll place images in word/media/ and add relationships
image_rels = {}  # filename -> rId
rId_counter = [10]  # start from rId10 to avoid conflicts with sample

for fname, caption, week in SCREENSHOTS:
    rId_counter[0] += 1
    rId = f"rId{rId_counter[0]}"
    image_rels[fname] = rId

# ── Build full document ───────────────────────────────────────────────────────
current_week = None
for week_num, wdata in week_data.items():
    add(para(wdata["title"], bold=True, size=24))
    add(para(wdata["subtitle"], bold=True, size=22))
    add(para(""))
    add(para(wdata["intro"]))
    add(para(""))
    add(para("WHAT I LEARNED", bold=True, size=20))
    for item in wdata["learned"]:
        bullet = para(f"- {item}")
        add(bullet)
    add(para(""))

    # Add comparison table for week 4
    if week_num == 4:
        add(para("Comparison: Existing System vs. Proposed CampusConnect System", bold=True, size=20))
        add(para(""))
        body.append(comparison_table())
        add(para("Table 2.1: Comparison between Existing and Proposed Systems", align="center"))
        add(para(""))

    add(para("CONCLUSION", bold=True, size=20))
    add(para(wdata["conclusion"]))
    add(para(""))

    # Screenshots for this week
    for fname, caption, _ in wdata["screenshots"]:
        rId = image_rels[fname]
        add(image_para(rId, width_emu=6000000, height_emu=3600000, caption=caption))
        add(para(""))

    add(page_break())

# ── Wrap in <w:document> ──────────────────────────────────────────────────────
doc_elm = ET.Element(f"{{{W}}}document")
doc_elm.append(body)
doc_xml = ET.tostring(doc_elm, encoding="unicode")
doc_xml = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>\n' + doc_xml

# ── Build new DOCX ZIP ────────────────────────────────────────────────────────
# Step 1: Read everything from sample into memory
with zipfile.ZipFile(SAMPLE_DOCX, "r") as zin:
    existing_names = set(zin.namelist())
    sample_files = {}
    for name in zin.namelist():
        sample_files[name] = zin.read(name)

# Step 2: Build updated rels XML with image relationships
rels_ns = "http://schemas.openxmlformats.org/package/2006/relationships"
img_rel_type = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/image"

if "word/_rels/document.xml.rels" in sample_files:
    rels_xml = sample_files["word/_rels/document.xml.rels"].decode("utf-8")
else:
    rels_xml = ('<?xml version="1.0" encoding="UTF-8" standalone="yes"?>'
                f'<Relationships xmlns="{rels_ns}"></Relationships>')

rels_tree = ET.fromstring(rels_xml)
for fname, caption, week in SCREENSHOTS:
    rId = image_rels[fname]
    rel = ET.SubElement(rels_tree, f"{{{rels_ns}}}Relationship")
    rel.set("Id", rId)
    rel.set("Type", img_rel_type)
    rel.set("Target", f"media/{fname}")

new_rels_xml = ('<?xml version="1.0" encoding="UTF-8" standalone="yes"?>\n'
                + ET.tostring(rels_tree, encoding="unicode"))

# Step 3: Write new DOCX
with zipfile.ZipFile(OUTPUT_DOCX, "w", zipfile.ZIP_DEFLATED) as zout:
    # Copy all sample files except document.xml and rels (will be replaced)
    for name, data in sample_files.items():
        if name in ("word/document.xml", "word/_rels/document.xml.rels"):
            continue
        zout.writestr(name, data)

    # Write updated document.xml
    zout.writestr("word/document.xml", doc_xml.encode("utf-8"))

    # Write updated rels
    zout.writestr("word/_rels/document.xml.rels", new_rels_xml.encode("utf-8"))

    # Embed screenshots into word/media/
    for fname in os.listdir(SCREENSHOTS_DIR):
        if fname.endswith(".png"):
            with open(os.path.join(SCREENSHOTS_DIR, fname), "rb") as f:
                zout.writestr(f"word/media/{fname}", f.read())

print("SUCCESS: Report saved to:", OUTPUT_DOCX)
print("Screenshots embedded:", len(SCREENSHOTS))
