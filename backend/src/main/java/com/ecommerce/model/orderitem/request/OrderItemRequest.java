package com.ecommerce.model.orderitem.request;

public record OrderItemRequest(
        String productId,
        int quantity
) {}
