package com.ecommerce.model.order.request;

import com.ecommerce.entity.address.Address;
import com.ecommerce.entity.customer.Customer;
import com.ecommerce.entity.orderitem.OrderItem;

import java.util.List;

public class OrderRequest {
    Customer customer;
    Address billingAddress;
    Address shippingAddress;
    String carrierId;
    String paymentId;
    double orderTotal;
    List<OrderItem> orderItems;
}
