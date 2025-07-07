package com.ecommerce.model.product.response;

import java.util.List;

public record ProductResponse(String id, String name, String descriptionShort, String descriptionLong, List<String> images,
                              double price, int stock) {
}
