package com.zest.assignment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zest.assignment.dto.AuthResponse;
import com.zest.assignment.dto.LoginRequest;
import com.zest.assignment.dto.RefreshRequest;
import com.zest.assignment.entity.RefreshToken;
import com.zest.assignment.entity.User;
import com.zest.assignment.exception.CustomException;
import com.zest.assignment.repository.RefreshTokenRepository;
import com.zest.assignment.repository.UserRepository;
import com.zest.assignment.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	@Autowired
    private UserRepository userRepository;
	@Autowired
    private RefreshTokenRepository refreshTokenRepository;
	@Autowired
    private JwtUtils jwtUtils;
	@Autowired
    private PasswordEncoder passwordEncoder;

    // LOGIN
    public AuthResponse login(LoginRequest request) {

        User admin = userRepository.findByEmail(request.getEmail());
                
         if (!"ROLE_ADMIN".equals(admin.getRole())) {
        	   throw new RuntimeException("Access denied. Not an admin.");
        }

        // Check password (BCrypt match)
         if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
             throw new CustomException(
                     "Invalid Credentials",
                     401,
                     List.of("Password incorrect")
             );
         }

        // Generate tokens
        String accessToken = jwtUtils.generateAccessToken(admin.getEmail(), admin.getRole());
        String refreshToken = jwtUtils.generateRefreshToken(admin.getEmail());

        // Save refresh token in DB
        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .user(admin) 
                .expiryDate(LocalDateTime.now().plusDays(7))
                .revoked(false)
                .build();

        refreshTokenRepository.save(token);

        return new AuthResponse(request.getEmail(),accessToken, refreshToken);
    }

    // LOGOUT
    public void logout(String refreshToken) {

        RefreshToken token = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid Refresh Token"));

        refreshTokenRepository.delete(token);
    }
    
    public AuthResponse refreshToken(RefreshRequest request) {

        String oldToken = request.getRefreshToken();

        // Find token in DB
        RefreshToken storedToken = refreshTokenRepository
                .findByToken(oldToken)
                .orElseThrow(() -> new RuntimeException("Invalid Refresh Token"));

        if (storedToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(storedToken);
            throw new RuntimeException("Refresh Token Expired");
        }

        User user = storedToken.getUser();

        refreshTokenRepository.delete(storedToken);

        // Generate New Tokens
        String newAccessToken =
                jwtUtils.generateAccessToken(user.getEmail(), user.getRole());

        String newRefreshToken =
                jwtUtils.generateRefreshToken(user.getEmail());

        RefreshToken newToken = RefreshToken.builder()
                .token(newRefreshToken)
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .revoked(false)
                .build();

        refreshTokenRepository.save(newToken);

        return new AuthResponse(user.getEmail(), newAccessToken, newRefreshToken);
    }
    
}