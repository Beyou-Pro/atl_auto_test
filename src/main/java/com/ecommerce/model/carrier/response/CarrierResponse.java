package com.ecommerce.model.carrier.response;

public record CarrierResponse(String name, String id, String serviceType, String areaServed, double averageRating,
                              double maxWeight, String contactEmail, String phone, String trackingUrlTemplate) {
}
