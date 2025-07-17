package com.ecommerce.model.address.response;

import com.ecommerce.entity.address.Address;

import java.util.UUID;

public record AddressResponse(
        UUID id,
        String street,
        String city,
        String zipcode,
        String country,
        String addressType
) {

    public static AddressResponse fromEntity(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getZipcode(),
                address.getCountry(),
                address.getAddressType()
        );
    }
}

