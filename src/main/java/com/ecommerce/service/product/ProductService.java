package com.ecommerce.service.product;

import com.ecommerce.model.filter.Filter;
import com.ecommerce.model.product.response.ProductResponse;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductService {
    List<ProductResponse> getProducts();

    List<ProductResponse> getFilteredProducts(Set<Filter> filters);

    List<ProductResponse> getProductsByName(String name);

    ProductResponse getProductById(String id);

}
