package com.zest.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zest.assignment.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
