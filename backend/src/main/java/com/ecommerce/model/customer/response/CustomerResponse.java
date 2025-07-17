package com.ecommerce.model.customer.response;

import com.ecommerce.entity.customer.Customer;

import java.util.UUID;

public record CustomerResponse(
        String id,
        String name,
        String email
) {

}

