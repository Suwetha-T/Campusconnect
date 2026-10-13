package com.sms.backend.service;

import com.sms.backend.dto.request.MarksRequest;
import com.sms.backend.dto.response.MarksResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MarksService {

    MarksResponse createMarks(MarksRequest request);

    MarksResponse getMarksById(Long id);

    List<MarksResponse> getAllMarks();

    MarksResponse updateMarks(Long id, MarksRequest request);

    void deleteMarks(Long id);

    List<MarksResponse> searchMarks(String keyword);

    Page<MarksResponse> getMarksPage(Pageable pageable);

    List<MarksResponse> getCurrentStudentMarks();
}