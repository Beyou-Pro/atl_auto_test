package com.ecommerce.model.address.request;

public record AddressRequest(String street,
                             String city,
                             String zipcode,
                             String country,
                             String addressType) {}
