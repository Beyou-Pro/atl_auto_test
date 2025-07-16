package com.ecommerce.repository.customer;

import com.ecommerce.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("""
            SELECT c
            FROM Customer c
            WHERE c.id = :id
            """)
    Optional<Customer> findByIdString(String id);
}
