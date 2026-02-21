package com.zest.assignment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zest.assignment.dto.OrderItemResponseDto;
import com.zest.assignment.dto.OrderRequestDto;
import com.zest.assignment.dto.OrderResponseDto;
import com.zest.assignment.entity.Order;
import com.zest.assignment.entity.OrderItem;
import com.zest.assignment.entity.Product;
import com.zest.assignment.entity.User;
import com.zest.assignment.repository.OrderRepository;
import com.zest.assignment.repository.ProductRepository;
import com.zest.assignment.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	@Autowired
    private OrderRepository orderRepository;
	@Autowired
    private ProductRepository productRepository;
	@Autowired
    private UserRepository userRepository;

    // 🔐 PLACE ORDER (Authenticated User)
    public OrderResponseDto placeOrder(String userEmail, OrderRequestDto dto) {

        User user = userRepository.findByEmail(userEmail);

        Order order = Order.builder()
                .user(user)
                .address(dto.getAddress())
                .orderDate(LocalDateTime.now())
                .build();

        List<OrderItem> orderItems = dto.getItems().stream().map(i -> {

            Product product = productRepository.findById(i.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            return OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(i.getQuantity())
                    .build();

        }).toList();

        order.setOrderItems(orderItems);

        Order saved = orderRepository.save(order);

        return mapToResponse(saved);
    }

    // 🔐 GET MY ORDERS
    public List<OrderResponseDto> getMyOrders(String userEmail) {

        User user = userRepository.findByEmail(userEmail);

        return orderRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔁 Mapper
    private OrderResponseDto mapToResponse(Order order) {

        List<OrderItemResponseDto> items = order.getOrderItems()
                .stream()
                .map(i -> OrderItemResponseDto.builder()
                        .productId(i.getProduct().getId())
                        .productName(i.getProduct().getProductName())
                        .quantity(i.getQuantity())
                        .build())
                .toList();

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .address(order.getAddress())
                .orderDate(order.getOrderDate())
                .items(items)
                .build();
    }
}
