package com.ecommerce.service.order.impl;

import com.ecommerce.entity.order.Order;
import com.ecommerce.entity.orderitem.OrderItem;
import com.ecommerce.model.order.request.OrderRequest;
import com.ecommerce.model.order.response.OrderResponse;
import com.ecommerce.repository.order.OrderRepository;
import com.ecommerce.service.order.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .customer(orderRequest.customer())
                .billingAddress(orderRequest.billingAddress())
                .shippingAddress(orderRequest.shippingAddress())
                .carrierId(orderRequest.carrierId())
                .paymentId(orderRequest.paymentId())
                .orderTotal(orderRequest.orderTotal())
                .orderDate(new Date())
                .status("CREATED")
                .items(orderRequest.orderItems())
                .build();

        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                item.setOrder(order);
            }
        }

        Order savedOrder = orderRepository.save(order);
        return OrderResponse.fromEntity(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        return OrderResponse.fromEntity(order);
    }

    @Override
    public List<OrderResponse> getOrdersByCustomer(UUID customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public OrderResponse updateOrderStatus(UUID orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);

        return OrderResponse.fromEntity(updatedOrder);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }
}
