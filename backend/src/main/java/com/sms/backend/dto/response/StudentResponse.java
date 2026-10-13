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
public class StudentResponse {

    private Long id;

    private Long userId;

    private String userEmail;

    private Long departmentId;

    private String departmentName;

    private String enrollmentNumber;

    private String firstName;

    private String lastName;

    private String phone;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String address;

    private String profilePhoto;

    private Integer semester;

    private String academicYear;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}