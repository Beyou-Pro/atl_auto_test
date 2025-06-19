package com.ecommerce.repository.orderitem;

import com.ecommerce.entity.orderitem.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    @Query("""
            SELECT oi FROM OrderItem oi WHERE oi.sessionId = :sessionId
            """)
    List<OrderItem> findBySessionId(String sessionId);

    @Transactional
    @Modifying
    @Query("DELETE FROM OrderItem oi WHERE oi.sessionId = :sessionId AND oi.product = :productId")
    void deleteBySessionIdAndProductId(String sessionId, UUID productId);

}
