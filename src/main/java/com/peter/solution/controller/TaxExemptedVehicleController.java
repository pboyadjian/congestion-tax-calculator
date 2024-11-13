package com.peter.solution.controller;

import com.peter.solution.repository.vehicle.TaxExemptedVehicle;
import com.peter.solution.repository.vehicle.TaxExemptedVehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tax-exempted-vehicles")
@RequiredArgsConstructor
public class TaxExemptedVehicleController {

    private final TaxExemptedVehicleRepository taxExemptedVehicleRepository;

    /**
     * Retrieves all tax-exempt vehicles.
     *
     * @return a list of tax-exempt vehicles
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaxExemptedVehicle> getAllExemptedVehicles() {
        return taxExemptedVehicleRepository.findAll();
    }

    /**
     * Adds a new tax-exempt vehicle type.
     *
     * @param vehicle the vehicle type to add
     * @return the saved vehicle type
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TaxExemptedVehicle addExemptedVehicle(@RequestBody TaxExemptedVehicle vehicle) {
        return taxExemptedVehicleRepository.save(vehicle);
    }

    /**
     * Deletes a tax-exempt vehicle by ID.
     *
     * @param id the ID of the vehicle to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExemptedVehicle(@PathVariable Long id) {
        taxExemptedVehicleRepository.deleteById(id);
    }
}