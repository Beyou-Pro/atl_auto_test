package com.ecommerce.model.product.response;

import com.ecommerce.model.product.AbstractProduct;
import lombok.Getter;

@Getter
public class ProductResponse extends AbstractProduct {
    private final String id;
    private final String name;
    private final String descriptionShort;
    private final String descriptionLong;
    private final String images;
    private final double price;
    private final int stock;

    public ProductResponse(String id, String name, String descriptionShort, String descriptionLong, String images, double price, int stock) {
        super(name);
        this.id = id;
        this.name = name;
        this.descriptionShort = descriptionShort;
        this.descriptionLong = descriptionLong;
        this.images = images;
        this.price = price;
        this.stock = stock;
    }
}
