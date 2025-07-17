package com.ecommerce.service.order.impl;

import com.ecommerce.entity.address.Address;
import com.ecommerce.entity.customer.Customer;
import com.ecommerce.entity.order.Order;
import com.ecommerce.entity.orderitem.OrderItem;
import com.ecommerce.model.order.request.OrderRequest;
import com.ecommerce.model.order.response.OrderResponse;
import com.ecommerce.repository.order.OrderRepository;
import com.ecommerce.service.address.AddressService;
import com.ecommerce.service.customer.CustomerService;
import com.ecommerce.service.order.OrderService;
import com.ecommerce.service.orderitem.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final CustomerService customerService;
    private final AddressService addressService;
    private final OrderItemService orderItemService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerService customerService, AddressService addressService, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.addressService = addressService;
        this.orderItemService = orderItemService;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Customer customer = customerService.getOrCreateCustomer(orderRequest.customerId());
        Address billingAddress = addressService.createAndSaveAddress(orderRequest.billingAddress());
        Address shippingAddress = addressService.createAndSaveAddress(orderRequest.shippingAddress());
        List<OrderItem> items = orderItemService.createOrderItems(orderRequest.orderItems());

        Order order = createOrderEntity(customer, billingAddress, shippingAddress, orderRequest, items);

        return OrderResponse.fromEntity(orderRepository.save(order));
    }

    private Order createOrderEntity(Customer customer, Address billing, Address shipping,
                                    OrderRequest orderRequest, List<OrderItem> items) {

        Order order = Order.builder()
                .customer(customer)
                .billingAddress(billing)
                .shippingAddress(shipping)
                .carrierId(orderRequest.carrierId())
                .paymentId(orderRequest.paymentId())
                .orderDate(new Date())
                .orderTotal(items.stream().mapToDouble(OrderItem::getTotalPrice).sum())
                .status("CREATED")
                .items(items)
                .build();

        items.forEach(item -> item.setOrder(order));

        return order;
    }

    @Override
    public OrderResponse getOrderById(UUID orderId) {
        return OrderResponse.fromEntity(orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId)));
    }

    @Override
    public List<OrderResponse> getOrdersByCustomer(UUID customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional
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
