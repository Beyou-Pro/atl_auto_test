package com.ecommerce.service.payment.impl;

import com.ecommerce.model.payment.PaymentResponse;
import com.ecommerce.repository.payment.PaymentRepository;
import com.ecommerce.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.getAllPayments();
    }

    @Override
    public PaymentResponse getPaymentById(String id) {
        return paymentRepository.getPaymentById(id);
    }
}
