package com.sms.backend.controller;

import com.sms.backend.dto.request.FacultyRequest;
import com.sms.backend.dto.response.FacultyResponse;
import com.sms.backend.service.FacultyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/faculty")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @GetMapping("/me")
    public FacultyResponse getCurrentFacultyProfile() {
        return facultyService.getCurrentFacultyProfile();
    }

    @PostMapping
    public FacultyResponse createFaculty(
            @Valid @RequestBody FacultyRequest request) {

        return facultyService.createFaculty(request);
    }

    @GetMapping("/{id}")
    public FacultyResponse getFacultyById(
            @PathVariable Long id) {

        return facultyService.getFacultyById(id);
    }

    @GetMapping
    public List<FacultyResponse> getAllFaculty() {

        return facultyService.getAllFaculty();
    }

    @GetMapping("/search")
    public List<FacultyResponse> searchFaculty(
            @RequestParam String keyword) {

        return facultyService.searchFaculty(keyword);
    }

    @GetMapping("/page")
    public Page<FacultyResponse> getFacultyPage(
            Pageable pageable) {

        return facultyService.getFacultyPage(pageable);
    }

    @PutMapping("/{id}")
    public FacultyResponse updateFaculty(
            @PathVariable Long id,
            @Valid @RequestBody FacultyRequest request) {

        return facultyService.updateFaculty(id, request);
    }

   @DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteFaculty(
        @PathVariable Long id) {

    facultyService.deleteFaculty(id);

}

}