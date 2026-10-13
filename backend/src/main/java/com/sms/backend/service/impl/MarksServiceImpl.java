package com.sms.backend.service.impl;

import com.sms.backend.dto.request.MarksRequest;
import com.sms.backend.dto.response.MarksResponse;
import com.sms.backend.entity.Marks;
import com.sms.backend.entity.StudentCourse;
import com.sms.backend.exception.ResourceNotFoundException;
import com.sms.backend.mapper.MarksMapper;
import com.sms.backend.repository.MarksRepository;
import com.sms.backend.repository.StudentCourseRepository;
import com.sms.backend.repository.StudentRepository;
import com.sms.backend.service.MarksService;
import com.sms.backend.util.SecurityUtils;
import com.sms.backend.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarksServiceImpl implements MarksService {

    private final MarksRepository marksRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final StudentRepository studentRepository;

    @Override
    public MarksResponse createMarks(MarksRequest request) {

        StudentCourse studentCourse = studentCourseRepository.findByStudent_IdAndCourse_Id(request.getStudentId(), request.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student Course not found for given student and course."));

        Marks marks = MarksMapper.toEntity(request, studentCourse);

        Marks savedMarks = marksRepository.save(marks);

        return MarksMapper.toResponse(savedMarks);
    }

    @Override
    public MarksResponse getMarksById(Long id) {

        Marks marks = marksRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Marks not found."));

        return MarksMapper.toResponse(marks);
    }

    @Override
    public List<MarksResponse> getAllMarks() {

        return marksRepository.findAll()
                .stream()
                .map(MarksMapper::toResponse)
                .toList();
    }

    @Override
    public MarksResponse updateMarks(Long id, MarksRequest request) {

        Marks marks = marksRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Marks not found."));

        StudentCourse studentCourse = studentCourseRepository.findByStudent_IdAndCourse_Id(request.getStudentId(), request.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student Course not found for given student and course."));

        marks.setStudentCourse(studentCourse);
        marks.setStudent(studentCourse.getStudent());
        marks.setExamType(request.getExamType());
        marks.setMarksObtained(request.getMarksObtained());
        marks.setMaximumMarks(request.getMaximumMarks());

        Marks updatedMarks = marksRepository.save(marks);

        return MarksMapper.toResponse(updatedMarks);
    }

    @Override
    public void deleteMarks(Long id) {

        Marks marks = marksRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Marks not found."));

        marksRepository.delete(marks);
    }

    @Override
    public List<MarksResponse> searchMarks(String keyword) {

        return marksRepository
                .findByStudent_FirstNameContainingIgnoreCase(keyword)
                .stream()
                .map(MarksMapper::toResponse)
                .toList();
    }

    @Override
    public Page<MarksResponse> getMarksPage(Pageable pageable) {

        return marksRepository
                .findAll(pageable)
                .map(MarksMapper::toResponse);
    }

    @Override
    public List<MarksResponse> getCurrentStudentMarks() {

        Student student = studentRepository
                .findByUser_Email(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student profile not found."));

        return marksRepository.findByStudent_Id(student.getId())
                .stream()
                .map(MarksMapper::toResponse)
                .toList();
    }
}