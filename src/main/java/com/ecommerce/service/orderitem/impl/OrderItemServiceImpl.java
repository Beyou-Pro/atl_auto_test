package com.ecommerce.service.orderitem.impl;

import com.ecommerce.entity.orderitem.OrderItem;
import com.ecommerce.model.orderitem.request.OrderItemRequest;
import com.ecommerce.model.product.response.ProductResponse;
import com.ecommerce.repository.product.ProductRepository;
import com.ecommerce.service.orderitem.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final ProductRepository productRepository;

    @Autowired
    public OrderItemServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderItem> createOrderItems(List<OrderItemRequest> itemRequests) {
        return itemRequests.stream()
                .map(request -> {
                    ProductResponse product = productRepository.getProductById(request.productId());
                    return OrderItem.builder()
                            .productId(product.id())
                            .quantity(request.quantity())
                            .unitPrice(product.price())
                            .totalPrice(request.quantity() * product.price())
                            .build();
                })
                .toList();
    }
}
