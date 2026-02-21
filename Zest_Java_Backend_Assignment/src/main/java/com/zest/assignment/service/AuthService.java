package com.zest.assignment.service;

import org.springframework.stereotype.Service;

import com.zest.assignment.entity.RefreshToken;
import com.zest.assignment.entity.User;
import com.zest.assignment.repository.RefreshTokenRepository;
import com.zest.assignment.repository.UserRepository;
import com.zest.assignment.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.zest.assignment.dto.AuthResponse;
import com.zest.assignment.dto.LoginRequest;
import com.zest.assignment.dto.RefreshRequest;
import com.zest.assignment.dto.UserRegisterRequest;
import com.zest.assignment.exception.CustomException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;

    public User register(UserRegisterRequest req) {

        // Check Email Exists
        if (userRepository.existsByEmail(req.getEmail())) {
        	throw new IllegalArgumentException ("Email Is Already Registered");
        }

        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role("ROLE_USER")
                .build();

        return userRepository.save(user);
    }
    
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
        	 throw new CustomException("Invalid Credentials",401,List.of("Email not found") );        
        	 }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(
                    "Invalid Credentials",
                    401,
                    List.of("Password incorrect")
            );
        }

        // Generate Tokens
        String accessToken =jwtUtils.generateAccessToken(user.getEmail(), user.getRole());

        String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());

        // Delete old refresh token (rotation ready)
        refreshTokenRepository.deleteByUser(user);

        // Save new refresh token
        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .revoked(false)
                .build();

        refreshTokenRepository.save(token);

        return new AuthResponse(request.getEmail(),accessToken, refreshToken);
    }

    public void logout(String refreshToken) {

        RefreshToken token = refreshTokenRepository
                .findByToken(refreshToken)
                .orElseThrow(() ->
                        new CustomException(
                                "Invalid Refresh Token",
                                400,
                                List.of("Token not found")
                        )
                );

        token.setRevoked(true);
        refreshTokenRepository.save(token);
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