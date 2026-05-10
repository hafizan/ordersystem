package com.mb.ordersystem.mapper;

import com.mb.ordersystem.domain.Order;
import com.mb.ordersystem.dto.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customer.id",   target = "customerId")
    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "product.id",    target = "productId")
    @Mapping(source = "product.name",  target = "productName")
    OrderResponse toResponse(Order order);
}
