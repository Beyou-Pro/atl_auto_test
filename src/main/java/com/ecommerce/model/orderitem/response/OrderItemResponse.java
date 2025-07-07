package com.ecommerce.model.orderitem.response;

import com.ecommerce.entity.orderitem.OrderItem;

import java.util.UUID;

public record OrderItemResponse(
        UUID id,
        String productId,
        int quantity,
        double unitPrice,
        double totalPrice,
        String sessionId
) {
    public static OrderItemResponse fromEntity(OrderItem item) {
        if (item == null) return null;
        return new OrderItemResponse(
                item.getId(),
                item.getProductId(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getTotalPrice(),
                item.getSessionId()
        );
    }
}

