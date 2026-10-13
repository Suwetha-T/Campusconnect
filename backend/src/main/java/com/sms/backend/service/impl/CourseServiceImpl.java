package com.sms.backend.service.impl;

import com.sms.backend.dto.request.CourseRequest;
import com.sms.backend.dto.response.CourseResponse;
import com.sms.backend.entity.Course;
import com.sms.backend.entity.Department;
import com.sms.backend.entity.Faculty;
import com.sms.backend.exception.DuplicateResourceException;
import com.sms.backend.exception.ResourceNotFoundException;
import com.sms.backend.mapper.CourseMapper;
import com.sms.backend.repository.CourseRepository;
import com.sms.backend.repository.DepartmentRepository;
import com.sms.backend.repository.FacultyRepository;
import com.sms.backend.service.CourseService;
import com.sms.backend.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;

    @Override
    public CourseResponse createCourse(CourseRequest request) {

        if (courseRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Course code already exists.");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .or(() -> departmentRepository.findByCode(String.valueOf(request.getDepartmentId())))
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .or(() -> facultyRepository.findByEmployeeCode(String.valueOf(request.getFacultyId())))
                .orElseThrow(() ->
                        new ResourceNotFoundException("Faculty not found."));

        Course course = CourseMapper.toEntity(request, department, faculty);

        Course savedCourse = courseRepository.save(course);

        return CourseMapper.toResponse(savedCourse);
    }

    @Override
    public CourseResponse getCourseById(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found."));

        return CourseMapper.toResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(CourseMapper::toResponse)
                .toList();
    }

    @Override
    public CourseResponse updateCourse(Long id,
                                       CourseRequest request) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found."));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .or(() -> departmentRepository.findByCode(String.valueOf(request.getDepartmentId())))
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .or(() -> facultyRepository.findByEmployeeCode(String.valueOf(request.getFacultyId())))
                .orElseThrow(() ->
                        new ResourceNotFoundException("Faculty not found."));

        if (!course.getCode().equals(request.getCode())
                && courseRepository.existsByCode(request.getCode())) {

            throw new DuplicateResourceException("Course code already exists.");

        }

        course.setDepartment(department);
        course.setFaculty(faculty);

        course.setCode(request.getCode());
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setCredits(request.getCredits());
        course.setSemester(request.getSemester());

        Course updatedCourse = courseRepository.save(course);

        return CourseMapper.toResponse(updatedCourse);

    }

    @Override
    public void deleteCourse(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found."));

        courseRepository.delete(course);

    }

    @Override
    public List<CourseResponse> searchCourses(String keyword) {

        return courseRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(CourseMapper::toResponse)
                .toList();

    }

    @Override
    public Page<CourseResponse> getCoursesPage(Pageable pageable) {

        return courseRepository
                .findAll(pageable)
                .map(CourseMapper::toResponse);

    }

    @Override
    public List<CourseResponse> getFacultyAssignedCourses() {

        Faculty faculty = facultyRepository
                .findByUser_Email(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Faculty profile not found."));

        return courseRepository.findByFaculty_Id(faculty.getId())
                .stream()
                .map(CourseMapper::toResponse)
                .toList();
    }
}