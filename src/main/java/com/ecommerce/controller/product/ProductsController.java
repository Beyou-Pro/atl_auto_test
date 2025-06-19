package com.ecommerce.controller.product;

import com.ecommerce.entity.product.Product;
import com.ecommerce.model.filter.Filter;
import com.ecommerce.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all products")
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getProducts();
    }

    @Operation(summary = "Get filtered products by filter set")
    @GetMapping("/filters")
    public List<Product> getFilteredProducts(@RequestParam Set<Filter> filters) {
        return productService.getFilteredProducts(filters);
    }

    @Operation(summary = "Get products by name")
    @GetMapping("/name")
    public List<Product> getFilteredProductsByName(@RequestParam String name) {
        return productService.getProductsByName(name);
    }

    @Operation(summary = "Get product details by ID")
    @GetMapping("/{id}")
    public Product getProductDetails(@PathVariable String id) {
        return productService.getProductById(id);
    }
}
