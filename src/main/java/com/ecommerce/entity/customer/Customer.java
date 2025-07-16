package com.ecommerce.entity.customer;

import com.ecommerce.entity.address.Address;
import com.ecommerce.entity.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Builder
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    private String id;

    private String name;
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_ship_address_id")
    private Address defaultShipAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_billing_address_id")
    private Address defaultBillingAddress;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;
}

