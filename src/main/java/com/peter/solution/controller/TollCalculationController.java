package com.peter.solution.controller;

import com.peter.solution.dto.VehicleDTO;
import com.peter.solution.service.TaxRateCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/toll")
@RequiredArgsConstructor
public class TollCalculationController {

    private final TaxRateCalculator taxRateCalculator;

    /**
     * Calculate the toll for a vehicle.
     *
     * @param vehicle the vehicle object
     * @return the calculated toll for the vehicle
     */
    @PostMapping(value = "/calculate", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleDTO calculateToll(@RequestBody VehicleDTO vehicle) {
        double amount = taxRateCalculator.calculate(vehicle);
        vehicle.setTaxAmount(amount);
        return vehicle;
    }
}