package com.ecommerce.service.address;

import com.ecommerce.entity.address.Address;
import com.ecommerce.model.address.request.AddressRequest;

public interface AddressService {
    Address createAndSaveAddress(AddressRequest request);
}
