package com.zest.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zest.assignment.dto.AuthResponse;
import com.zest.assignment.dto.LoginRequest;
import com.zest.assignment.dto.RefreshRequest;
import com.zest.assignment.dto.UserRegisterRequest;
import com.zest.assignment.entity.User;
import com.zest.assignment.service.AuthService;
import com.zest.assignment.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody @Valid UserRegisterRequest req) {

        User data = service.register(req);

        ApiResponse<User> response =
                new ApiResponse<>(201, data, "User registered successfully");

        return ResponseEntity.status(201).body(response);
    }
    
    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid LoginRequest request) {

        AuthResponse data = service.login(request);

        ApiResponse<AuthResponse> response =
                new ApiResponse<>(200, data, "Login successful");

        return ResponseEntity.ok(response);
    }

    // LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestParam String refreshToken) {

        service.logout(refreshToken);

        ApiResponse<String> response =
                new ApiResponse<>(200, null, "Logout successful");

        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestBody RefreshRequest request) {

        AuthResponse response = service.refreshToken(request);

        return ResponseEntity.ok(
                new ApiResponse<>(200, response, "Token refreshed successfully")
        );
    }
    
    
}