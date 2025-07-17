package com.ecommerce.service.carrier;

import com.ecommerce.model.carrier.response.CarrierResponse;

import java.util.List;

public interface CarrierService {

    List<CarrierResponse> getCarriers();

    CarrierResponse getCarrierById(String id);
}
