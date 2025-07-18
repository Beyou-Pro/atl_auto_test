package com.ecommerce.repository.orderitem;

import com.ecommerce.entity.orderitem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
