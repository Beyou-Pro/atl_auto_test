package com.ecommerce.repository.carrier;

import com.ecommerce.model.carrier.response.CarrierResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "carrierRepository", url = "${json.api.url}")
public interface CarrierRepository {

    @GetMapping("/carriers")
    List<CarrierResponse> getAllCarriers();

    @GetMapping("/carriers/{id}")
    CarrierResponse getCarrierById(@PathVariable("id") String id);
}