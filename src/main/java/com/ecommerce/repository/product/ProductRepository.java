package com.ecommerce.repository.product;

import com.ecommerce.entity.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "productClient", url = "${json.api.url}")
public interface ProductRepository {

    @GetMapping("/products")
    List<Product> getAllProducts();

    @GetMapping("/products")
    List<Product> getProductsByName(@RequestParam("name") String name);

    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable("id") String id);
}

