package com.zest.assignment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponseDto {

    private Long productId;
    private String productName;
    private Integer quantity;
}
