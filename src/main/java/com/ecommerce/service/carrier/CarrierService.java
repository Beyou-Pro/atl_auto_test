package com.ecommerce.service.carrier;


import com.ecommerce.entity.carrier.Carrier;

import java.util.List;

public interface CarrierService {
    List<Carrier> getCarriers();

    Carrier getCarrierById(String id);
}
