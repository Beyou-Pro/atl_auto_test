package com.ecommerce.service.customer;

import com.ecommerce.entity.customer.Customer;
import jakarta.transaction.Transactional;

import java.util.UUID;

public interface CustomerService {
    Customer getOrCreateCustomer(UUID customerId);
}
