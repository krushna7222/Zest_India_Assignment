package com.zest.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zest.assignment.entity.Order;
import com.zest.assignment.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
