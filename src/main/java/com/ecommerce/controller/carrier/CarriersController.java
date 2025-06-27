package com.ecommerce.controller.carrier;

import com.ecommerce.model.carrier.response.CarrierResponse;
import com.ecommerce.service.carrier.CarrierService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carriers")
public class CarriersController {

    private final CarrierService carrierService;

    @Autowired
    public CarriersController(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    @Operation(summary = "Get all carriers")
    @GetMapping
    public List<CarrierResponse> getAllCarriers() {
        return carrierService.getCarriers();
    }

    @Operation(summary = "Get carrier details by ID")
    @GetMapping("/{id}")
    public CarrierResponse getCarrierDetails(@PathVariable String id) {
        return carrierService.getCarrierById(id);
    }
}
