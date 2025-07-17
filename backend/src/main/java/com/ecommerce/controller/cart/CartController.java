package com.ecommerce.controller.cart;

import com.ecommerce.model.cart.CartItem;
import com.ecommerce.model.cart.request.CartItemRequest;
import com.ecommerce.model.product.response.ProductResponse;
import com.ecommerce.service.product.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;

    @Autowired
    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        if (session.getAttribute("customerUUID") == null) {
            String customerUUID = java.util.UUID.randomUUID().toString();
            session.setAttribute("customerUUID", customerUUID);
            System.out.println("New customerUUID generated: " + customerUUID);
        }

        return cart;
    }


    @PostMapping("/add")
    public void addToCart(@RequestBody CartItemRequest request, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        ProductResponse product = productService.getProductById(request.productId());
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        Optional<CartItem> existing = cart.stream()
                .filter(ci -> ci.getProductId().equals(request.productId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + request.quantity());
        } else {
            CartItem newItem = new CartItem(
                    product.id(),
                    product.name(),
                    product.price(),
                    request.quantity()
            );
            cart.add(newItem);
        }

        session.setAttribute("cart", cart);
    }


    @PostMapping("/remove")
    public void removeFromCart(@RequestParam String productId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getProductId().equals(productId));
            session.setAttribute("cart", cart);
        }
    }


    @PostMapping("/clear")
    public void clearCart(HttpSession session) {
        session.removeAttribute("cart");
    }
}

