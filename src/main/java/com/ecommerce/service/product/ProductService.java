package com.ecommerce.service.product;

import com.ecommerce.entity.product.Product;
import com.ecommerce.model.filter.Filter;

import java.util.List;
import java.util.Set;

public interface ProductService {
    List<Product> getProducts();

    List<Product> getFilteredProducts(Set<Filter> filters);

    List<Product> getProductsByName(String name);

    Product getProductById(String id);

}
