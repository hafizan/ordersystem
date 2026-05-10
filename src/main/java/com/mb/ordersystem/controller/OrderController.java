package com.mb.ordersystem.controller;

import com.mb.ordersystem.common.ApiResponse;
import com.mb.ordersystem.dto.request.OrderRequest;
import com.mb.ordersystem.dto.request.UpdateOrderStatusRequest;
import com.mb.ordersystem.dto.response.OrderResponse;
import com.mb.ordersystem.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ApiResponse<Page<OrderResponse>> findAll(@PageableDefault(size = 10) Pageable pageable) {
        return ApiResponse.ok(orderService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> findById(@PathVariable Long id) {
        return ApiResponse.ok(orderService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest request) {
        return ApiResponse.ok("placeOrder", orderService.placeOrder(request));
    }

    @PutMapping("/{id}/status")
    public ApiResponse<OrderResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateOrderStatusRequest request) {
        return ApiResponse.ok("updateStatus", orderService.updateStatus(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ApiResponse.ok("cancelOrder", null);
    }
}
