package com.ecommerce.service.carrier.impl;

import com.ecommerce.entity.carrier.Carrier;
import com.ecommerce.repository.carrier.CarrierRepository;
import com.ecommerce.service.carrier.CarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarrierServiceImpl implements CarrierService {

    private final CarrierRepository carrierRepository;

    @Autowired
    public CarrierServiceImpl(CarrierRepository carrierRepository) {
        this.carrierRepository = carrierRepository;
    }

    @Override
    public List<Carrier> getCarriers() {
        return carrierRepository.getAllCarriers();
    }

    @Override
    public Carrier getCarrierById(String id) {
        return carrierRepository.getCarrierById(id);
    }
}
