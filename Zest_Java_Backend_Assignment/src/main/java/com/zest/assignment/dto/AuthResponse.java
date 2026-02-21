package com.zest.assignment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

	private String email;
    private String accessToken;
    private String refreshToken;
}