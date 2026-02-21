package com.zest.assignment.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequestDto {

    private String address;

    private List<OrderItemRequestDto> items;
}
