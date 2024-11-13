package com.peter.solution.service;

import com.peter.solution.dto.VehicleDTO;

/**
 * Interface that defines the contract for calculating the toll fee for a vehicle
 * based on its type, date, time, and exemption rules.
 */
public interface TaxRateCalculator {

    /**
     * Calculates the toll fee for a given vehicle.
     *
     * @param vehicle the vehicle for which the toll fee is calculated
     * @return the calculated toll fee
     */
    double calculate(VehicleDTO vehicle);
}