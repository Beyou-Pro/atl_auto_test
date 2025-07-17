package com.ecommerce.model.order.request;

import com.ecommerce.model.address.request.AddressRequest;
import com.ecommerce.model.orderitem.request.OrderItemRequest;

import java.util.List;
import java.util.UUID;

public record OrderRequest(
        UUID customerId,
        AddressRequest billingAddress,
        AddressRequest shippingAddress,
        String carrierId,
        String paymentId,
        List<OrderItemRequest> orderItems
) {}
