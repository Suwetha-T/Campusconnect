package com.sms.backend.controller;

import com.sms.backend.dto.request.MarksRequest;
import com.sms.backend.dto.response.MarksResponse;
import com.sms.backend.service.MarksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marks")
@RequiredArgsConstructor
public class MarksController {

    private final MarksService marksService;

    @PostMapping
    public MarksResponse createMarks(
            @Valid @RequestBody MarksRequest request) {

        return marksService.createMarks(request);
    }

    @GetMapping("/me")
    public List<MarksResponse> getCurrentStudentMarks() {
        return marksService.getCurrentStudentMarks();
    }


    @GetMapping("/{id}")
    public MarksResponse getMarksById(
            @PathVariable Long id) {

        return marksService.getMarksById(id);
    }

    @GetMapping
    public List<MarksResponse> getAllMarks() {

        return marksService.getAllMarks();
    }

    @GetMapping("/search")
    public List<MarksResponse> searchMarks(
            @RequestParam String keyword) {

        return marksService.searchMarks(keyword);
    }

    @GetMapping("/page")
    public Page<MarksResponse> getMarksPage(
            Pageable pageable) {

        return marksService.getMarksPage(pageable);
    }

    @PutMapping("/{id}")
    public MarksResponse updateMarks(
            @PathVariable Long id,
            @Valid @RequestBody MarksRequest request) {

        return marksService.updateMarks(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteMarks(
            @PathVariable Long id) {

        marksService.deleteMarks(id);

        return "Marks deleted successfully.";
    }

}