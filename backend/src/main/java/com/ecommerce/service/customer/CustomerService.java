package com.ecommerce.service.customer;

import com.ecommerce.entity.customer.Customer;

import java.util.UUID;

public interface CustomerService {
    Customer getOrCreateCustomer(UUID customerId);
}
