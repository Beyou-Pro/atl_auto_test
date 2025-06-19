package com.ecommerce.controller.order;

import com.ecommerce.entity.order.Order;
import com.ecommerce.entity.orderitem.OrderItem;
import com.ecommerce.entity.product.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
@SessionAttributes("order")
public class OrderController {

    @ModelAttribute("order")
    public Order createOrder() {
        return new Order();
    }

    @GetMapping("/step1")
    public String showStep1() {
        return "order-step1";
    }

    @PostMapping("/step1")
    public String processStep1(@ModelAttribute("order") Order order, @RequestParam Product product) {
        order.getItems().add(new OrderItem());
        return "redirect:/order/step2";
    }

    @GetMapping("/step2")
    public String showStep2() {
        return "order-step2";
    }

    @PostMapping("/step2")
    public String processStep2(@ModelAttribute("order") Order order, @RequestParam String shippingAddress) {
        order.setShippingAddress(shippingAddress);
        return "redirect:/order/step3";
    }

    @GetMapping("/step3")
    public String showStep3() {
        return "order-step3"; // payment info
    }

    @PostMapping("/step3")
    public String processStep3(@ModelAttribute("order") Order order, @RequestParam String paymentMethod) {
        order.setPaymentMethod(paymentMethod);
        order.setConfirmed(true);
        return "order-confirmation";
    }
}

