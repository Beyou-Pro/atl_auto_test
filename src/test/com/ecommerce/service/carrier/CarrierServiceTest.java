package com.ecommerce.service.carrier;

import com.ecommerce.model.carrier.response.CarrierResponse;
import com.ecommerce.repository.carrier.CarrierRepository;
import com.ecommerce.service.carrier.CarrierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarrierServiceTest {

    private CarrierRepository carrierRepository;
    private CarrierService carrierService;

    @BeforeEach
    void setUp() {
        carrierRepository = mock(CarrierRepository.class);
        carrierService = mock(CarrierService.class);
    }

    @Test
    void testGetCarriers() {
        List<CarrierResponse> carriers = carrierService.getCarriers();

        assertNotNull(carriers);
    }

    @Test
    void testGetCarrierById() {
        CarrierResponse result = carrierService.getCarrierById("trk001");

        assertNotNull(result);
    }
}
