package com.ecommerce.model.customer.response;

import com.ecommerce.entity.customer.Customer;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String name,
        String email
) {
    public static CustomerResponse fromEntity(Customer customer) {
        if (customer == null) return null;
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }
}

