package com.zest.assignment.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponseDto {

    private Long orderId;
    private String address;
    private LocalDateTime orderDate;
    private List<OrderItemResponseDto> items;
}
