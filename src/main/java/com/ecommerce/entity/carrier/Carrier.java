package com.ecommerce.entity.carrier;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Carrier {

    @Id
    private String id;

    private String name;
    private String serviceType;
    private String areaServed;
    private double averageRating;
    private double maxWeight;
    private String contactEmail;
    private String phone;
    private String trackingUrlTemplate;

    @ElementCollection
    @CollectionTable(name = "carrier_features", joinColumns = @JoinColumn(name = "carrier_id"))
    @Column(name = "feature")
    private List<String> features;
}
