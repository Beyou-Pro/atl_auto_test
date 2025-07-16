package com.ecommerce.model.order.request;

import com.ecommerce.entity.address.Address;
import com.ecommerce.entity.customer.Customer;
import com.ecommerce.entity.orderitem.OrderItem;
import com.ecommerce.model.address.request.AddressRequest;
import com.ecommerce.model.orderitem.request.OrderItemRequest;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

public record OrderRequest(
        String customerId,
        AddressRequest billingAddress,
        AddressRequest shippingAddress,
        String carrierId,
        String paymentId,
        double orderTotal,
        List<OrderItemRequest> orderItems
) {}
