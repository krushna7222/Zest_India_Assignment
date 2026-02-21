package com.zest.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zest.assignment.dto.ItemResponseDto;
import com.zest.assignment.dto.ProductRequestDto;
import com.zest.assignment.dto.ProductResponseDto;
import com.zest.assignment.entity.Item;
import com.zest.assignment.entity.Product;
import com.zest.assignment.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	

	@Autowired
	private ProductRepository productRepository;

	// ADD PRODUCT
    public ProductResponseDto addProduct(ProductRequestDto dto) {

        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setCreatedBy(dto.getCreatedBy());

        List<Item> items = dto.getItems().stream().map(i -> {
            Item item = Item.builder()
                    .quantity(i.getQuantity())
                    .product(product)
                    .build();
            return item;
        }).toList();

        product.setItems(items);

        Product saved = productRepository.save(product);

        return mapToResponse(saved);
    }

    // UPDATE PRODUCT
    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setProductName(dto.getProductName());
        product.setModifiedBy(dto.getCreatedBy());

        product.getItems().clear();

        List<Item> newItems = dto.getItems().stream().map(i -> {
            Item item = Item.builder()
                    .quantity(i.getQuantity())
                    .product(product)
                    .build();
            return item;
        }).toList();

        product.getItems().addAll(newItems);

        Product updated = productRepository.save(product);

        return mapToResponse(updated);
    }

    // DELETE PRODUCT
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }

    // GET ALL
    public List<ProductResponseDto> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ GET SINGLE
    public ProductResponseDto getProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapToResponse(product);
    }

    // 🔁 Mapper
    private ProductResponseDto mapToResponse(Product product) {

        List<ItemResponseDto> itemDtos = product.getItems()
                .stream()
                .map(i -> ItemResponseDto.builder()
                        .id(i.getId())
                        .quantity(i.getQuantity())
                        .build())
                .toList();

        return ProductResponseDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .createdBy(product.getCreatedBy())
                .createdOn(product.getCreatedOn())
                .modifiedBy(product.getModifiedBy())
                .modifiedOn(product.getModifiedOn())
                .items(itemDtos)
                .build();
    }
	
}
