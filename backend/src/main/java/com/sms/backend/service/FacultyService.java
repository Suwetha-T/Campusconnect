package com.sms.backend.service;

import com.sms.backend.dto.request.FacultyRequest;
import com.sms.backend.dto.response.FacultyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FacultyService {

    FacultyResponse createFaculty(FacultyRequest request);

    FacultyResponse getFacultyById(Long id);

    List<FacultyResponse> getAllFaculty();

    FacultyResponse updateFaculty(Long id, FacultyRequest request);

    void deleteFaculty(Long id);

    List<FacultyResponse> searchFaculty(String keyword);

    Page<FacultyResponse> getFacultyPage(Pageable pageable);

    FacultyResponse getCurrentFacultyProfile();

}