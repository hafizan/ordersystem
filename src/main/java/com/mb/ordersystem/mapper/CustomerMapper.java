package com.mb.ordersystem.mapper;

import com.mb.ordersystem.domain.Customer;
import com.mb.ordersystem.dto.request.CustomerRequest;
import com.mb.ordersystem.dto.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse toResponse(Customer customer);

    Customer toEntity(CustomerRequest request);

    void updateEntity(CustomerRequest request, @MappingTarget Customer customer);
}
