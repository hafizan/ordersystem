package com.mb.ordersystem.service;

import com.mb.ordersystem.domain.Order;
import com.mb.ordersystem.domain.OrderStatus;
import com.mb.ordersystem.dto.request.OrderRequest;
import com.mb.ordersystem.dto.request.UpdateOrderStatusRequest;
import com.mb.ordersystem.dto.response.OrderResponse;
import com.mb.ordersystem.exception.InsufficientStockException;
import com.mb.ordersystem.exception.ResourceNotFoundException;
import com.mb.ordersystem.mapper.OrderMapper;
import com.mb.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerService customerService;
    private final ProductService productService;

    public Page<OrderResponse> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toResponse);
    }

    public OrderResponse findById(Long id) {
        return orderMapper.toResponse(getOrderOrThrow(id));
    }

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        var customer = customerService.getCustomerOrThrow(request.getCustomerId());
        var product = productService.getProductOrThrow(request.getProductId());

        if (product.getStockQty() < request.getQuantity()) {
            throw new InsufficientStockException(request.getQuantity(), product.getStockQty());
        }

        product.setStockQty(product.getStockQty() - request.getQuantity());

        var order = Order.builder()
                .customer(customer)
                .product(product)
                .quantity(request.getQuantity())
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())))
                .status(OrderStatus.PENDING)
                .build();

        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse updateStatus(Long id, UpdateOrderStatusRequest request) {
        var order = getOrderOrThrow(id);
        order.setStatus(request.getStatus());
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Transactional
    public void cancelOrder(Long id) {
        var order = getOrderOrThrow(id);
        order.setStatus(OrderStatus.CANCELLED);
        var product = order.getProduct();
        product.setStockQty(product.getStockQty() + order.getQuantity());
        orderRepository.save(order);
    }

    private Order getOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
    }
}
