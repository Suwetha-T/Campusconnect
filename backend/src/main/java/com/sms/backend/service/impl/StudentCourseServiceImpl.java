package com.sms.backend.service.impl;

import com.sms.backend.dto.request.StudentCourseRequest;
import com.sms.backend.dto.response.StudentCourseResponse;
import com.sms.backend.entity.Course;
import com.sms.backend.entity.Student;
import com.sms.backend.entity.StudentCourse;
import com.sms.backend.exception.ResourceNotFoundException;
import com.sms.backend.mapper.StudentCourseMapper;
import com.sms.backend.repository.CourseRepository;
import com.sms.backend.repository.StudentCourseRepository;
import com.sms.backend.repository.StudentRepository;
import com.sms.backend.service.StudentCourseService;
import com.sms.backend.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public StudentCourseResponse createStudentCourse(StudentCourseRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found."));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found."));

        StudentCourse studentCourse = studentCourseRepository
                .findByStudent_IdAndCourse_Id(request.getStudentId(), request.getCourseId())
                .orElseGet(() -> StudentCourseMapper.toEntity(request, student, course));

        if (studentCourse.getId() != null) {
            studentCourse.setEnrollmentDate(request.getEnrollmentDate());
            studentCourse.setEnrollmentStatus(request.getEnrollmentStatus());
        }

        StudentCourse savedStudentCourse =
                studentCourseRepository.save(studentCourse);

        return StudentCourseMapper.toResponse(savedStudentCourse);
    }

    @Override
    public StudentCourseResponse getStudentCourseById(Long id) {

        StudentCourse studentCourse = studentCourseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student Course not found."));

        return StudentCourseMapper.toResponse(studentCourse);
    }

    @Override
    public List<StudentCourseResponse> getAllStudentCourses() {

        return studentCourseRepository.findAll()
                .stream()
                .map(StudentCourseMapper::toResponse)
                .toList();
    }

    @Override
    public StudentCourseResponse updateStudentCourse(Long id,
                                                     StudentCourseRequest request) {

        StudentCourse studentCourse = studentCourseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student Course not found."));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found."));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found."));

        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        studentCourse.setEnrollmentDate(request.getEnrollmentDate());
        studentCourse.setEnrollmentStatus(request.getEnrollmentStatus());

        StudentCourse updatedStudentCourse =
                studentCourseRepository.save(studentCourse);

        return StudentCourseMapper.toResponse(updatedStudentCourse);
    }

    @Override
    public void deleteStudentCourse(Long id) {

        StudentCourse studentCourse = studentCourseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student Course not found."));

        studentCourseRepository.delete(studentCourse);
    }

    @Override
    public List<StudentCourseResponse> getCurrentStudentCourses() {

        Student student = studentRepository
                .findByUser_Email(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student profile not found."));

        return studentCourseRepository.findByStudent_Id(student.getId())
                .stream()
                .map(StudentCourseMapper::toResponse)
                .toList();
    }

    @Override
    public List<StudentCourseResponse> getStudentCoursesByStudentId(Long studentId) {
        return studentCourseRepository.findByStudent_Id(studentId)
                .stream()
                .map(StudentCourseMapper::toResponse)
                .toList();
    }
}