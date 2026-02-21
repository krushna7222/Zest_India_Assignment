package com.zest.assignment.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductRequestDto {

    @NotBlank
    private String productName;

    @NotBlank
    private String createdBy;

    private List<ItemRequestDto> items;
}
