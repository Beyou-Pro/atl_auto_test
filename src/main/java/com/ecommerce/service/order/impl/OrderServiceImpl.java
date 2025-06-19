package com.ecommerce.service.order.impl;

import com.ecommerce.entity.orderitem.OrderItem;
import com.ecommerce.entity.product.Product;
import com.ecommerce.repository.orderitem.OrderItemRepository;
import com.ecommerce.repository.product.ProductRepository;
import com.ecommerce.service.order.OrderService;

import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderItemRepository orderItemRepository, ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    public List<OrderItem> getOrderItems(String sessionId) {
        return orderItemRepository.findBySessionId(sessionId);
    }

    public void addToCart(String sessionId, String productId, int quantity) {
        Product product = productRepository.getProductById(productId);
        if (product.getStock() < quantity) throw new RuntimeException("Stock insuffisant");

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setUnitPrice(product.getPrice());
        item.setTotalPrice(product.getPrice() * quantity);
        item.setSessionId(sessionId);

        orderItemRepository.save(item);
    }

    public void updateOrderItem(String sessionId, String productId, int quantity) {
        List<OrderItem> items = orderItemRepository.findBySessionId(sessionId);
        for (OrderItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(item.getUnitPrice() * quantity);
                orderItemRepository.save(item);
                return;
            }
        }
    }

    public void removeOrderItem(String sessionId, UUID productId) {
        orderItemRepository.deleteBySessionIdAndProductId(sessionId, productId);
    }

    public double getCartSubtotal(String sessionId) {
        return orderItemRepository.findBySessionId(sessionId)
                .stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
    }

}
