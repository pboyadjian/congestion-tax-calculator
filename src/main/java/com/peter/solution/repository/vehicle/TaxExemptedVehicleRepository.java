package com.peter.solution.repository.vehicle;

import com.peter.solution.repository.InMemoryRepository;
import org.springframework.stereotype.Component;

/**
 * Repository class for managing tax-exempted vehicles.
 * Inherits from the InMemoryRepository to provide basic CRUD operations for vehicle types.
 * This repository is pre-populated with a set of tax-exempted vehicle types.
 */
@Component
public class TaxExemptedVehicleRepository extends InMemoryRepository<TaxExemptedVehicle> {

    public TaxExemptedVehicleRepository() {
        this.save(TaxExemptedVehicle.ofVehicle("Motorcycle"));
        this.save(TaxExemptedVehicle.ofVehicle("Tractor"));
        this.save(TaxExemptedVehicle.ofVehicle("Emergency"));
        this.save(TaxExemptedVehicle.ofVehicle("Diplomat"));
        this.save(TaxExemptedVehicle.ofVehicle("Foreign"));
        this.save(TaxExemptedVehicle.ofVehicle("Military"));
    }

    /**
     * Saves a vehicle type in the repository only if it is not already present.
     *
     * @param vehicle the vehicle type to save
     * @return the vehicle type that was saved (or already exists)
     */
    @Override
    public TaxExemptedVehicle save(TaxExemptedVehicle vehicle) {
        boolean isPresent = findAll().stream()
                .map(TaxExemptedVehicle::getVehicle)
                .anyMatch(vehicle.getVehicle()::equals);
        if (!isPresent) super.save(vehicle);
        return vehicle;
    }

}