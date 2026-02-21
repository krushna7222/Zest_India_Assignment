package com.zest.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zest.assignment.dto.OrderRequestDto;
import com.zest.assignment.dto.OrderResponseDto;
import com.zest.assignment.service.OrderService;
import com.zest.assignment.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class OrderController {

	@Autowired
    private OrderService service;

    // PLACE ORDER
    @PostMapping("/order")
    public ResponseEntity<ApiResponse<OrderResponseDto>> placeOrder(@RequestBody @Valid OrderRequestDto dto,Authentication authentication) {

        String email = authentication.getName();

        OrderResponseDto data = service.placeOrder(email, dto);

        return ResponseEntity.status(201)
                .body(new ApiResponse<>(201, data, "Order placed successfully"));
    }

    // GET MY ORDERS
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getMyOrders(Authentication authentication) {

        String email = authentication.getName();

        List<OrderResponseDto> data = service.getMyOrders(email);

        return ResponseEntity.ok(
                new ApiResponse<>(200, data, "Orders fetched successfully"));
    }
}
