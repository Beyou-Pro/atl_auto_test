package com.ecommerce.model.order.response;

import com.ecommerce.entity.order.Order;
import com.ecommerce.model.address.response.AddressResponse;
import com.ecommerce.model.customer.response.CustomerResponse;
import com.ecommerce.model.orderitem.response.OrderItemResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record OrderResponse(
        UUID id,
        Date orderDate,
        String status,
        CustomerResponse customer,
        AddressResponse shippingAddress,
        AddressResponse billingAddress,
        String carrierId,
        String paymentId,
        double orderTotal,
        List<OrderItemResponse> items
) {
    public static OrderResponse fromEntity(Order order) {
        List<OrderItemResponse> itemResponses = null;

        if (!order.getItems().isEmpty()) {
            itemResponses = order.getItems()
                    .stream()
                    .map(OrderItemResponse::fromEntity)
                    .collect(Collectors.toList());
        }

        return new OrderResponse(
                order.getId(),
                order.getOrderDate(),
                order.getStatus(),
                CustomerResponse.fromEntity(order.getCustomer()),
                AddressResponse.fromEntity(order.getShippingAddress()),
                AddressResponse.fromEntity(order.getBillingAddress()),
                order.getCarrierId(),
                order.getPaymentId(),
                order.getOrderTotal(),
                itemResponses
        );
    }
}

