package com.ecommerce.model.address.response;

import com.ecommerce.entity.address.Address;
import com.ecommerce.model.customer.response.CustomerResponse;

import java.util.UUID;

public record AddressResponse(
        UUID id,
        String street,
        String city,
        String zipcode,
        String country,
        String addressType,
        CustomerResponse customer
) {

    public static AddressResponse fromEntity(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getZipcode(),
                address.getCountry(),
                address.getAddressType(),
                CustomerResponse.fromEntity(address.getCustomer())
        );
    }
}

