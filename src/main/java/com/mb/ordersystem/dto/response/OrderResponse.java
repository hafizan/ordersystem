package com.mb.ordersystem.dto.response;

import com.mb.ordersystem.domain.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderResponse {

    private Long id;
    private Long customerId;
    private String customerName;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
