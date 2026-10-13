package com.sms.backend.mapper;

import com.sms.backend.dto.request.RegisterRequest;
import com.sms.backend.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(RegisterRequest request,
                                String encodedPassword) {

        return User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .role(request.getRole())
                .isActive(true)
                .build();
    }
}