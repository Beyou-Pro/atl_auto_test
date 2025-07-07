package com.ecommerce.service.order;

import com.ecommerce.entity.order.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(Order order);

    Order getOrderById(UUID id);

    List<Order> getOrdersByCustomer(UUID customerId);

    Order updateOrderStatus(UUID orderId, String status);

    void deleteOrder(UUID orderId);
}
