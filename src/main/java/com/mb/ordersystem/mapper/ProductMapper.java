package com.mb.ordersystem.mapper;

import com.mb.ordersystem.domain.Product;
import com.mb.ordersystem.dto.request.ProductRequest;
import com.mb.ordersystem.dto.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toResponse(Product product);

    Product toEntity(ProductRequest request);
    void updateEntity(ProductRequest request, @MappingTarget Product product);
}
