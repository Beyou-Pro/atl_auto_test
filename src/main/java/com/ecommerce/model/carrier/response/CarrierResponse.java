package com.ecommerce.model.carrier.response;

import com.ecommerce.model.carrier.AbstractCarrier;
import lombok.Getter;

@Getter
public class CarrierResponse extends AbstractCarrier {
    private final String id;

    private final String serviceType;
    private final String areaServed;
    private final double averageRating;
    private final double maxWeight;
    private final String contactEmail;
    private final String phone;
    private final String trackingUrlTemplate;

    public CarrierResponse(String name, String id, String serviceType, String areaServed, double averageRating, double maxWeight, String contactEmail, String phone, String trackingUrlTemplate) {
        super(name);
        this.id = id;
        this.serviceType = serviceType;
        this.areaServed = areaServed;
        this.averageRating = averageRating;
        this.maxWeight = maxWeight;
        this.contactEmail = contactEmail;
        this.phone = phone;
        this.trackingUrlTemplate = trackingUrlTemplate;
    }
}
