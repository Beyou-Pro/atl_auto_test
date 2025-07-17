package com.ecommerce.repository.order;

import com.ecommerce.entity.order.Order;
import com.ecommerce.model.order.response.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<OrderResponse> findByCustomerId(UUID customerId);
}
