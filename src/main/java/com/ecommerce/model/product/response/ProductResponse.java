package com.ecommerce.model.product.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ProductResponse(String id, String name, @JsonProperty("description_short") String descriptionShort, @JsonProperty("description_long") String descriptionLong, List<String> images,
                              double price, int stock) {
}
