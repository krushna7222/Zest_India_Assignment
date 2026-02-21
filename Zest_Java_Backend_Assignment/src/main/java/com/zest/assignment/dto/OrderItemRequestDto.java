package com.zest.assignment.dto;

import lombok.Data;

@Data
public class OrderItemRequestDto {

    private Long productId;
    private Integer quantity;
}
