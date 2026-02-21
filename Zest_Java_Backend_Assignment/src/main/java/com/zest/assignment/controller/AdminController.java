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
import com.zest.assignment.service.AdminService;
import com.zest.assignment.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

	@Autowired
    private AdminService service;

	 @PostMapping("/login")
	    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {

	        AuthResponse response = service.login(request);

	        return ResponseEntity.ok(
	                new ApiResponse<>(200, response, "Login successful")
	        );
	    }

	    @PostMapping("/logout")
	    public ResponseEntity<ApiResponse<String>> logout(@RequestParam String refreshToken) {

	        service.logout(refreshToken);

	        return ResponseEntity.ok(
	                new ApiResponse<>(200, null, "Logout successful")
	        );
	    }
	    
	    @PostMapping("/refresh-token")
	    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestBody RefreshRequest request) {

	        AuthResponse response = service.refreshToken(request);

	        return ResponseEntity.ok(
	                new ApiResponse<>(200, response, "Token refreshed successfully")
	        );
	    }
	   
    
    
}