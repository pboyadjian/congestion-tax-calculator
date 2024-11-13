package com.peter.solution.service;

import com.peter.solution.dto.VehicleDTO;
import com.peter.solution.repository.dates.ExemptedDate;
import com.peter.solution.repository.dates.ExemptedDateRepository;
import com.peter.solution.repository.taxrate.TaxRate;
import com.peter.solution.repository.taxrate.TaxRateRepository;
import com.peter.solution.repository.tollpass.TollPass;
import com.peter.solution.repository.tollpass.TollPassRepository;
import com.peter.solution.repository.vehicle.TaxExemptedVehicle;
import com.peter.solution.repository.vehicle.TaxExemptedVehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Service implementation that calculates the toll fee for a vehicle based on its type, the date,
 * and its toll pass history.
 */
@Service
@RequiredArgsConstructor
public class TaxRateCalculatorImpl implements TaxRateCalculator {

    private final ExemptedDateRepository exemptedDateRepository;
    private final TaxRateRepository taxRateRepository;
    private final TaxExemptedVehicleRepository exemptedVehicleRepository;
    private final TollPassRepository tollPassRepository;

    /**
     * Calculates the toll fee for a given vehicle.
     *
     * @param vehicle the vehicle for which the toll fee is calculated
     * @return the calculated toll fee
     */
    @Override
    public double calculate(VehicleDTO vehicle) {
        LocalDateTime dateTime = vehicle.getTollPassDateTime();

        if (isVehicleExempted(vehicle) || isDateExempted(dateTime)) return 0;

        double toll = calculateToll(dateTime);

        String plateNumber = vehicle.getPlateNumber();
        LocalDate date = dateTime.toLocalDate();
        List<TollPass> tollPassesForToday = tollPassRepository.findAllByPlateNumberAndDateTime(
                plateNumber, date.atStartOfDay(), date.atTime(23, 59, 59));

        double highestToll = tollPassesForToday.stream()
                .filter(tollPass -> isWithinOneHour(tollPass.getPassDateTime(), dateTime))
                .mapToDouble(TollPass::getTollAmount)
                .max()
                .orElse(toll);

        toll = Math.min(highestToll, 60);

        saveTollPass(vehicle, toll);

        return toll;
    }

    /**
     * Checks if two toll passes are within 60 minutes of each other.
     *
     * @param passTime  the time of an existing toll pass
     * @param entryTime the time of the current toll pass
     * @return true if the toll passes are within 60 minutes of each other, false otherwise
     */
    private boolean isWithinOneHour(LocalDateTime passTime, LocalDateTime entryTime) {
        Duration duration = Duration.between(passTime, entryTime);
        return duration.abs().toMinutes() <= 60;
    }

    /**
     * Calculates the toll based on the vehicle's time of entry.
     *
     * @param dateTime the date and time when the vehicle enters the toll area
     * @return the calculated toll fee
     */
    private double calculateToll(LocalDateTime dateTime) {
        LocalTime gateTime = dateTime.toLocalTime();
        return taxRateRepository.findAll().stream()
                .filter(taxRate -> gateTime.isAfter(taxRate.getStartTime()) && gateTime.isBefore(taxRate.getEndTime()))
                .mapToDouble(TaxRate::getAmount)
                .findFirst()
                .orElse(0);
    }

    /**
     * Saves the toll pass record for the vehicle for a specific date and toll fee.
     *
     * @param vehicle the vehicle for which the toll pass is recorded
     * @param toll    the toll fee charged for the pass
     */
    private void saveTollPass(VehicleDTO vehicle, double toll) {
        TollPass tollPass = new TollPass(vehicle.getPlateNumber(), vehicle.getTollPassDateTime(), toll);
        tollPassRepository.save(tollPass);
    }

    /**
     * Checks if the vehicle is exempted from paying toll based on its type.
     *
     * @param vehicle the vehicle to check
     * @return true if the vehicle is exempted, false otherwise
     */
    private boolean isVehicleExempted(VehicleDTO vehicle) {
        return exemptedVehicleRepository.findAll().stream()
                .map(TaxExemptedVehicle::getVehicle)
                .anyMatch(vehicle.getType()::equals);
    }

    /**
     * Checks if the given date and time are exempted from toll charges (e.g., holidays, weekends).
     *
     * @param dateTime the date and time to check
     * @return true if the date is exempted, false otherwise
     */
    private boolean isDateExempted(LocalDateTime dateTime) {
        List<ExemptedDate> exemptedDates = exemptedDateRepository.findAll();
        return exemptedDates.stream().anyMatch(exemptedDate -> isDateExemptedForType(exemptedDate, dateTime));
    }

    /**
     * Determines if a specific date is exempted based on the type of exemption (month, day of the week, or holiday).
     *
     * @param exemptedDate the exempted date record to check
     * @param dateTime     the date and time to check against
     * @return true if the date matches the exempted date for the given type, false otherwise
     */
    private boolean isDateExemptedForType(ExemptedDate exemptedDate, LocalDateTime dateTime) {
        ExemptedDate.Type dateType = exemptedDate.getType();
        return switch (dateType) {
            case MONTH -> exemptedDate.getMonth().equals(dateTime.getMonth());
            case DAY_OF_WEEK -> exemptedDate.getDayOfWeek().equals(dateTime.getDayOfWeek());
            case HOLIDAY_DATE -> exemptedDate.getHolidayDate().equals(dateTime.toLocalDate());
        };
    }
}