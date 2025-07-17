package com.ecommerce.service.carrier.impl;

import com.ecommerce.model.carrier.response.CarrierResponse;
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
    public List<CarrierResponse> getCarriers() {
        return carrierRepository.getAllCarriers();
    }

    @Override
    public CarrierResponse getCarrierById(String id) {
        return carrierRepository.getCarrierById(id);
    }
}
