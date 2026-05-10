package com.mb.ordersystem.service;

import com.mb.ordersystem.domain.Product;
import com.mb.ordersystem.dto.request.ProductRequest;
import com.mb.ordersystem.dto.response.ProductResponse;
import com.mb.ordersystem.exception.ResourceNotFoundException;
import com.mb.ordersystem.mapper.ProductMapper;
import com.mb.ordersystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    
    private final ProductMapper productMapper;

    public Page<ProductResponse> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toResponse);
    }

    public ProductResponse findById(Long id) {
        return productMapper.toResponse(getProductOrThrow(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        return productMapper.toResponse(productRepository.save(productMapper.toEntity(request)));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        var product = getProductOrThrow(id);
        productMapper.updateEntity(request, product);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        productRepository.delete(getProductOrThrow(id));
    }

    public Product getProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }
}
