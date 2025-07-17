package com.ecommerce.service.product.impl;

import com.ecommerce.model.filter.Filter;
import com.ecommerce.model.product.response.ProductResponse;
import com.ecommerce.repository.product.ProductRepository;
import com.ecommerce.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponse> getProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public List<ProductResponse> getFilteredProducts(Set<Filter> filters) {
        List<ProductResponse> products = productRepository.getAllProducts();
        return products.stream()
                .filter(p -> filters.stream().allMatch(f -> f.matches(p)))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByName(String name) {
        return productRepository.getProductsByName(name);
    }

    @Override
    public ProductResponse getProductById(String id) {
        return productRepository.getProductById(id);
    }
}


