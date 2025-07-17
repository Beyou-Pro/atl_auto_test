package com.ecommerce.service.orderitem;

import com.ecommerce.entity.orderitem.OrderItem;
import com.ecommerce.model.orderitem.request.OrderItemRequest;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> createOrderItems(List<OrderItemRequest> itemRequests);
}
