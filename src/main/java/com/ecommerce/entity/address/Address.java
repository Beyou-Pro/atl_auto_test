package com.ecommerce.entity.address;

import java.util.UUID;

import com.ecommerce.entity.customer.Customer;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue
    private UUID id;

    private String street;
    private String city;
    private String zipcode;
    private String country;

    private String addressType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Customer customer;
}


