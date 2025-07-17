package com.ecommerce.service.order;

import com.ecommerce.model.order.request.OrderRequest;
import com.ecommerce.model.order.response.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);

    OrderResponse getOrderById(UUID id);

    List<OrderResponse> getOrdersByCustomer(UUID customerId);

    OrderResponse updateOrderStatus(UUID orderId, String status);

    void deleteOrder(UUID orderId);
}
