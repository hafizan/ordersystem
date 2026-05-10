package com.mb.ordersystem.repository;

import com.mb.ordersystem.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
        // Required if latr need to ad d new requirements

}
