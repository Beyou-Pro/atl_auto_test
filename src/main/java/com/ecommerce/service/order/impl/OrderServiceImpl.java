package com.ecommerce.service.order.impl;

import com.ecommerce.entity.address.Address;
import com.ecommerce.entity.customer.Customer;
import com.ecommerce.entity.order.Order;
import com.ecommerce.entity.orderitem.OrderItem;
import com.ecommerce.model.order.request.OrderRequest;
import com.ecommerce.model.order.response.OrderResponse;
import com.ecommerce.model.product.response.ProductResponse;
import com.ecommerce.repository.address.AddressRepository;
import com.ecommerce.repository.customer.CustomerRepository;
import com.ecommerce.repository.order.OrderRepository;
import com.ecommerce.repository.orderitem.OrderItemRepository;
import com.ecommerce.repository.product.ProductRepository;
import com.ecommerce.service.customer.CustomerService;
import com.ecommerce.service.order.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    private final CustomerService customerService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, CustomerRepository customerRepository, AddressRepository addressRepository, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.customerService = customerService;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Customer customer = customerService.getOrCreateCustomer(orderRequest.customerId());

        Address billingAddress = Address.builder()
                .street(orderRequest.billingAddress().street())
                .city(orderRequest.billingAddress().city())
                .zipcode(orderRequest.billingAddress().zipcode())
                .country(orderRequest.billingAddress().country())
                .addressType(orderRequest.billingAddress().addressType())
                .build();

        Address shippingAddress = Address.builder()
                .street(orderRequest.shippingAddress().street())
                .city(orderRequest.shippingAddress().city())
                .zipcode(orderRequest.shippingAddress().zipcode())
                .country(orderRequest.shippingAddress().country())
                .addressType(orderRequest.shippingAddress().addressType())
                .build();

        billingAddress = addressRepository.save(billingAddress);
        shippingAddress = addressRepository.save(shippingAddress);

        List<OrderItem> items = orderRequest.orderItems().stream()
                .map(orderItemRequest -> {
                    ProductResponse product = productRepository.getProductById(orderItemRequest.productId());
                    return OrderItem.builder()
                            .productId(product.id())
                            .quantity(orderItemRequest.quantity())
                            .unitPrice(product.price())
                            .totalPrice(orderItemRequest.quantity() * product.price())
                            .build();
                })
                .toList();



        Order order = Order.builder()
                .customer(customer)
                .billingAddress(billingAddress)
                .shippingAddress(shippingAddress)
                .carrierId(orderRequest.carrierId())
                .paymentId(orderRequest.paymentId())
                .orderDate(new Date())
                .items(items)
                .orderTotal(items.stream()
                        .mapToDouble(OrderItem::getTotalPrice)
                        .sum())
                .status("CREATED")
                .build();

        for (OrderItem item : items) {
            item.setOrder(order);
        }

        return OrderResponse.fromEntity(orderRepository.save(order));
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
