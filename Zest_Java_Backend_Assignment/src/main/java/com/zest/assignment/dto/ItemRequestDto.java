package com.zest.assignment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemRequestDto {

    @NotNull
    @Min(1)
    private Integer quantity;
}
