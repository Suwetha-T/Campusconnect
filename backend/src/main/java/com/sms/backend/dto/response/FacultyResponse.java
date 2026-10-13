package com.sms.backend.dto.response;

import com.sms.backend.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacultyResponse {

    private Long id;

    // User
    private Long userId;
    private String userEmail;

    // Department
    private Long departmentId;
    private String departmentName;

    // Faculty
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String phone;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String address;
    private String profilePhoto;

    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}