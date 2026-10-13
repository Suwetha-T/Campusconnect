package com.sms.backend.service;

import com.sms.backend.dto.request.StudentRequest;
import com.sms.backend.dto.response.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    StudentResponse createStudent(StudentRequest request);

    StudentResponse getStudentById(Long id);

    List<StudentResponse> getAllStudents();

    StudentResponse updateStudent(Long id, StudentRequest request);

    void deleteStudent(Long id);

    List<StudentResponse> searchStudents(String keyword);

    Page<StudentResponse> getStudentsPage(Pageable pageable);

    StudentResponse getCurrentStudentProfile();
}