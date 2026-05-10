package com.mb.ordersystem.service;

import com.mb.ordersystem.domain.Customer;
import com.mb.ordersystem.dto.request.CustomerRequest;
import com.mb.ordersystem.dto.response.CustomerResponse;
import com.mb.ordersystem.exception.ResourceNotFoundException;
import com.mb.ordersystem.mapper.CustomerMapper;
import com.mb.ordersystem.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public Page<CustomerResponse> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable).map(customerMapper::toResponse);
    }

    public CustomerResponse findById(Long id) {
        return customerMapper.toResponse(getCustomerOrThrow(id));
    }

    @Transactional
    public CustomerResponse create(CustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email already in use: " + request.getEmail());
        }
        return customerMapper.toResponse(customerRepository.save(customerMapper.toEntity(request)));
    }

    @Transactional
    public CustomerResponse update(Long id, CustomerRequest request) {
        var customer = getCustomerOrThrow(id);
        customerMapper.updateEntity(request, customer);
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Transactional
    public void delete(Long id) {
        customerRepository.delete(getCustomerOrThrow(id));
    }

    public Customer getCustomerOrThrow(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", id));
    }
}
