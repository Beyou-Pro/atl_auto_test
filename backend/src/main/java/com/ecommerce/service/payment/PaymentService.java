package com.ecommerce.service.payment;

import com.ecommerce.model.payment.PaymentResponse;

import java.util.List;

public interface PaymentService {
    List<PaymentResponse> getAllPayments();

    PaymentResponse getPaymentById(String id);
}
