package com.ecommerce.model.payment;

import java.util.Date;

public record PaymentResponse(String id, String order_id, String customer_id, String currency, String method,
                              String status, Date transaction_date, String processor_response, String notes) {
}
