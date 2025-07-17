package com.ecommerce.repository.product;

import com.ecommerce.model.product.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "productClient", url = "${json.api.url}")
public interface ProductRepository {

    @GetMapping("/products")
    List<ProductResponse> getAllProducts();

    @GetMapping("/products")
    List<ProductResponse> getProductsByName(@RequestParam("name") String name);

    @GetMapping("/products/{id}")
    ProductResponse getProductById(@PathVariable("id") String id);
}

