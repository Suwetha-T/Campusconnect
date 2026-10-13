package com.sms.backend.service;

import com.sms.backend.dto.request.LoginRequest;
import com.sms.backend.dto.request.RegisterRequest;
import com.sms.backend.dto.request.UserRequest;
import com.sms.backend.dto.response.AuthResponse;
import com.sms.backend.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    UserResponse createUser(UserRequest request);

    Page<UserResponse> getUsers(Pageable pageable);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UserRequest request);

    void toggleUserStatus(Long id);

    void deleteUser(Long id);

    List<UserResponse> searchUsers(String keyword);
}