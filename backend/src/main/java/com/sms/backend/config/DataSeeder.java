package com.sms.backend.config;

import com.sms.backend.entity.*;
import com.sms.backend.enums.EnrollmentStatus;
import com.sms.backend.enums.Gender;
import com.sms.backend.enums.Role;
import com.sms.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final AttendanceRepository attendanceRepository;
    private final MarksRepository marksRepository;
    private final AnnouncementRepository announcementRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("projectadmin@sms.com")) {
            User admin = User.builder()
                    .email("projectadmin@sms.com")
                    .password(passwordEncoder.encode("Admin@123"))
                    .role(Role.ADMIN)
                    .isActive(true)
                    .build();
            userRepository.save(admin);
        }

        Department department = null;
        if (departmentRepository.count() == 0) {
            department = Department.builder()
                    .name("Computer Science")
                    .code("CS")
                    .description("Computer Science Department")
                    .build();
            department = departmentRepository.save(department);
        } else {
            department = departmentRepository.findAll().get(0);
        }

        User facultyUser = userRepository.findByEmail("faculty@sms.com").orElse(null);
        if (facultyUser == null) {
            facultyUser = User.builder()
                    .email("faculty@sms.com")
                    .password(passwordEncoder.encode("Faculty@123"))
                    .role(Role.FACULTY)
                    .isActive(true)
                    .build();
            facultyUser = userRepository.save(facultyUser);
        }

        Faculty faculty = facultyRepository.findByUser_Email("faculty@sms.com").orElse(null);
        if (faculty == null) {
            String employeeCode = "EMP001";
            int employeeCodeIndex = 1;
            while (facultyRepository.existsByEmployeeCode(employeeCode)) {
                employeeCode = "EMP" + (++employeeCodeIndex);
            }

            faculty = Faculty.builder()
                    .user(facultyUser)
                    .department(department)
                    .employeeCode(employeeCode)
                    .firstName("John")
                    .lastName("Doe")
                    .phone("1234567890")
                    .gender(Gender.MALE)
                    .dateOfBirth(LocalDate.of(1980, 1, 1))
                    .address("123 Faculty St")
                    .build();
            faculty = facultyRepository.save(faculty);

            if (courseRepository.count() == 0) {
                Course course = Course.builder()
                        .faculty(faculty)
                        .department(department)
                        .name("Intro to Programming")
                        .code("CS101")
                        .description("Basic programming concepts")
                        .credits(3)
                        .semester(1)
                        .build();
                courseRepository.save(course);
            }
        }

        User studentUser = userRepository.findByEmail("student@sms.com").orElse(null);
        Student student1 = studentRepository.findByUser_Email("student@sms.com").orElse(null);
        if (studentUser == null) {
            studentUser = User.builder()
                    .email("student@sms.com")
                    .password(passwordEncoder.encode("Student@123"))
                    .role(Role.STUDENT)
                    .isActive(true)
                    .build();
            studentUser = userRepository.save(studentUser);
        }
        if (student1 == null) {
            String enrollmentNumber = "STU001";
            int enrollmentIndex = 1;
            while (studentRepository.existsByEnrollmentNumber(enrollmentNumber)) {
                enrollmentNumber = "STU" + (++enrollmentIndex);
            }

            student1 = Student.builder()
                    .user(studentUser)
                    .department(department)
                    .enrollmentNumber(enrollmentNumber)
                    .firstName("Jane")
                    .lastName("Smith")
                    .phone("0987654321")
                    .gender(Gender.FEMALE)
                    .dateOfBirth(LocalDate.of(2000, 1, 1))
                    .address("456 Student Ave")
                    .academicYear("2023-2024")
                    .semester(1)
                    .build();
            student1 = studentRepository.save(student1);
        }

        // Add a second student user for multi-student testing
        User studentUser2 = userRepository.findByEmail("student2@sms.com").orElse(null);
        Student student2 = studentRepository.findByUser_Email("student2@sms.com").orElse(null);
        if (studentUser2 == null) {
            studentUser2 = User.builder()
                    .email("student2@sms.com")
                    .password(passwordEncoder.encode("Student@123"))
                    .role(Role.STUDENT)
                    .isActive(true)
                    .build();
            studentUser2 = userRepository.save(studentUser2);
        }
        if (student2 == null) {
            String enrollmentNumber = "STU002";
            int enrollmentIndex = 2;
            while (studentRepository.existsByEnrollmentNumber(enrollmentNumber)) {
                enrollmentNumber = "STU" + (++enrollmentIndex);
            }

            student2 = Student.builder()
                    .user(studentUser2)
                    .department(department)
                    .enrollmentNumber(enrollmentNumber)
                    .firstName("Alex")
                    .lastName("Johnson")
                    .phone("1122334455")
                    .gender(Gender.MALE)
                    .dateOfBirth(LocalDate.of(2001, 5, 15))
                    .address("789 Student Blvd")
                    .academicYear("2023-2024")
                    .semester(1)
                    .build();
            student2 = studentRepository.save(student2);
        }

        // Enroll students in courses if studentCourse entries don't exist
        if (studentCourseRepository.count() == 0 && courseRepository.count() > 0) {
            Course course = courseRepository.findAll().get(0);
            if (student1 != null) {
                StudentCourse sc1 = StudentCourse.builder()
                        .student(student1)
                        .course(course)
                        .enrollmentDate(LocalDate.now())
                        .enrollmentStatus(EnrollmentStatus.ACTIVE)
                        .build();
                studentCourseRepository.save(sc1);
            }
            if (student2 != null) {
                StudentCourse sc2 = StudentCourse.builder()
                        .student(student2)
                        .course(course)
                        .enrollmentDate(LocalDate.now())
                        .enrollmentStatus(EnrollmentStatus.ACTIVE)
                        .build();
                studentCourseRepository.save(sc2);
            }
        }
        
        // Seed sample Attendance, Marks, and Announcements if empty
        if (studentCourseRepository.count() > 0) {
            StudentCourse sc = studentCourseRepository.findAll().get(0);
            
            if (attendanceRepository.count() == 0) {
                Attendance att1 = Attendance.builder()
                        .student(sc.getStudent())
                        .studentCourse(sc)
                        .attendanceDate(LocalDate.now().minusDays(1))
                        .attendanceStatus(com.sms.backend.enums.AttendanceStatus.PRESENT)
                        .build();
                Attendance att2 = Attendance.builder()
                        .student(sc.getStudent())
                        .studentCourse(sc)
                        .attendanceDate(LocalDate.now())
                        .attendanceStatus(com.sms.backend.enums.AttendanceStatus.PRESENT)
                        .build();
                attendanceRepository.saveAll(java.util.List.of(att1, att2));
            }

            if (marksRepository.count() == 0) {
                Marks mark1 = Marks.builder()
                        .student(sc.getStudent())
                        .studentCourse(sc)
                        .examType(com.sms.backend.enums.ExamType.MIDTERM)
                        .marksObtained(88.50)
                        .maximumMarks(100.00)
                        .build();
                marksRepository.save(mark1);
            }
        }

        if (announcementRepository.count() == 0 && facultyUser != null) {
            Announcement ann1 = Announcement.builder()
                    .createdBy(facultyUser)
                    .title("Mid-term Examination Schedule")
                    .content("Mid-term exams start next week. Please check your schedule.")
                    .target(com.sms.backend.enums.AnnouncementTarget.ALL)
                    .build();
            announcementRepository.save(ann1);
        }

        System.out.println("Default entities checked and seeded if necessary.");
    }
}
