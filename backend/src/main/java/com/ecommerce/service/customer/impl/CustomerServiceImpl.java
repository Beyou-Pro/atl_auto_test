package com.ecommerce.service.customer.impl;

import com.ecommerce.entity.customer.Customer;
import com.ecommerce.repository.customer.CustomerRepository;
import com.ecommerce.service.customer.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer getOrCreateCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseGet(() -> {
                    Customer guest = new Customer();
                    guest.setId(customerId);
                    return customerRepository.save(guest);
                });
    }

}
