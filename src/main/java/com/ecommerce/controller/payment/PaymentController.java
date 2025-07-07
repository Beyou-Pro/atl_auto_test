package com.ecommerce.controller.payment;

import com.ecommerce.model.payment.PaymentResponse;
import com.ecommerce.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Get all payments")
    @GetMapping
    public List<PaymentResponse> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @Operation(summary = "Get payment by ID")
    @GetMapping("/{id}")
    public PaymentResponse getPaymentDetails(@PathVariable String id) {
        return paymentService.getPaymentById(id);
    }
}
