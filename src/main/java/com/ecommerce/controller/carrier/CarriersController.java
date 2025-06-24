package com.ecommerce.controller.carrier;

import com.ecommerce.entity.carrier.Carrier;
import com.ecommerce.entity.product.Product;
import com.ecommerce.model.filter.Filter;
import com.ecommerce.service.carrier.CarrierService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    public List<Carrier> getAllCarriers() {
        return carrierService.getCarriers();
    }

    @Operation(summary = "Get carrier details by ID")
    @GetMapping("/{id}")
    public Carrier getCarrierDetails(@PathVariable String id) {
        return carrierService.getCarrierById(id);
    }
}
