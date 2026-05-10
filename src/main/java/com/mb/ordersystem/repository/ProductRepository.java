package com.mb.ordersystem.repository;

import com.mb.ordersystem.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Required if latr need to ad d new requirements
}
