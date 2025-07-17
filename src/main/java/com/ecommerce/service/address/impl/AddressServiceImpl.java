package com.ecommerce.service.address.impl;

import com.ecommerce.entity.address.Address;
import com.ecommerce.model.address.request.AddressRequest;
import com.ecommerce.repository.address.AddressRepository;
import com.ecommerce.service.address.AddressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Address createAndSaveAddress(AddressRequest request) {
        Address address = Address.builder()
                .street(request.street())
                .city(request.city())
                .zipcode(request.zipcode())
                .country(request.country())
                .addressType(request.addressType())
                .build();

        return addressRepository.save(address);
    }
}
