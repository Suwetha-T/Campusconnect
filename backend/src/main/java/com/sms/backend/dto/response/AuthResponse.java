package com.sms.backend.dto.response;

import com.sms.backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private Long id;

    private String email;

    private Role role;

    private String token;

    private String message;
}