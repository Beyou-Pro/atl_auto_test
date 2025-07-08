package com.ecommerce.controller.cart;

import com.ecommerce.model.cart.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @GetMapping
    public List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart.isEmpty()) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @PostMapping("/add")
    public String addToCart(@RequestBody CartItem item, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart.isEmpty()) {
            cart = new ArrayList<>();
        }

        Optional<CartItem> existing = cart.stream()
                .filter(ci -> ci.getProductId().equals(item.getProductId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + item.getQuantity());
        } else {
            cart.add(item);
        }

        session.setAttribute("cart", cart);
        return "Item added to cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam String productId, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (!cart.isEmpty()) {
            cart.removeIf(item -> item.getProductId().equals(productId));
            session.setAttribute("cart", cart);
        }
        return "Item removed";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "Cart cleared";
    }
}

