package com.sms.backend.dto.request;

import com.sms.backend.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long departmentId;

    @NotBlank
    private String enrollmentNumber;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String phone;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String address;

    private String profilePhoto;

    @NotNull
    private Integer semester;

    @NotBlank
    private String academicYear;
}