package com.ecommerce.repository.payment;

import com.ecommerce.model.carrier.response.CarrierResponse;
import com.ecommerce.model.payment.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "paymentRepository", url = "${json.api.url}")
public interface PaymentRepository {
    @GetMapping("/payments")
    List<PaymentResponse> getAllPayments();

    @GetMapping("/payments/{id}")
    PaymentResponse getPaymentById(@PathVariable("id") String id);
}
