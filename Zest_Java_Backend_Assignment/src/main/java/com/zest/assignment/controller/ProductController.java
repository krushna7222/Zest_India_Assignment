package com.zest.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zest.assignment.dto.ProductRequestDto;
import com.zest.assignment.dto.ProductResponseDto;
import com.zest.assignment.service.ProductService;
import com.zest.assignment.utils.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
	
	@Autowired
    private ProductService service;
	
	 // ADD
    @PostMapping("/admin/product/add")
    public ResponseEntity<ApiResponse<ProductResponseDto>> addProduct(@RequestBody @Valid ProductRequestDto dto) {

        ProductResponseDto data = service.addProduct(dto);

        return ResponseEntity.status(201)
                .body(new ApiResponse<>(201, data, "Product created successfully"));
    }

    // UPDATE
    @PutMapping("/admin/product/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(@PathVariable Long id,@RequestBody @Valid ProductRequestDto dto) {

        ProductResponseDto data = service.updateProduct(id, dto);

        return ResponseEntity.ok(
                new ApiResponse<>(200, data, "Product updated successfully"));
    }

    // DELETE
    @DeleteMapping("/admin/product/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long id) {

        service.deleteProduct(id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, null, "Product deleted successfully"));
    }

    // GET ALL
    @GetMapping("/product")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAll() {

        List<ProductResponseDto> data = service.getAllProducts();

        return ResponseEntity.ok(
                new ApiResponse<>(200, data, "Products fetched successfully"));
    }

    // GET SINGLE
    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getOne(@PathVariable Long id) {

        ProductResponseDto data = service.getProduct(id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, data, "Product fetched successfully"));
    }

}
