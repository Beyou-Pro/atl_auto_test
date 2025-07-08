package com.ecommerce.model.cart;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private String productId;
    private String name;
    private double price;
    private int quantity;
}

