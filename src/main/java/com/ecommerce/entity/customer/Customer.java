package com.ecommerce.entity.customer;

import com.ecommerce.entity.address.Address;
import com.ecommerce.entity.order.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "customers", schema = "ecommerce")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String name;
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_shipping_address_id", unique = true)
    private Address defaultShipAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_billing_address_id", unique = true)
    private Address defaultBillingAddress;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;
}

