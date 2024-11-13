package com.peter.solution.service;

import com.peter.solution.dto.VehicleDTO;
import com.peter.solution.repository.dates.ExemptedDateRepository;
import com.peter.solution.repository.taxrate.TaxRateRepository;
import com.peter.solution.repository.tollpass.TollPass;
import com.peter.solution.repository.tollpass.TollPassRepository;
import com.peter.solution.repository.vehicle.TaxExemptedVehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaxRateCalculatorTest {

    private TaxRateCalculatorImpl taxRateCalculator;

    @Mock
    private TollPassRepository tollPassRepository;

    @BeforeEach
    void setUp() {
        taxRateCalculator = new TaxRateCalculatorImpl(
                new ExemptedDateRepository(),
                new TaxRateRepository(),
                new TaxExemptedVehicleRepository(),
                tollPassRepository
        );
    }

    @Test
    void testCalculateTollForExemptedVehicle() {
        // Arrange
        VehicleDTO vehicle = new VehicleDTO("Motorcycle", "XYZ999");

        // Act
        double toll = taxRateCalculator.calculate(vehicle);

        // Assert
        assertEquals(0, toll);
    }

    @Test
    void testCalculateTollForExemptedDate() {
        // Arrange
        LocalDateTime firstOfJuly = LocalDateTime.of(2024, Month.JULY, 1, 0, 0);
        VehicleDTO vehicle = new VehicleDTO("Car", "XYZ999", firstOfJuly);

        // Act
        double toll = taxRateCalculator.calculate(vehicle);

        // Assert
        assertEquals(0, toll);
    }

    @Test
    void testCalculateTollForVehicleWithNoTollPass() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.of(2024, Month.NOVEMBER, 13, 6, 20);
        VehicleDTO vehicle = new VehicleDTO("Car", "XYZ999", dateTime);

        // Act
        double toll = taxRateCalculator.calculate(vehicle);

        // Assert
        assertEquals(8, toll);
    }

    @Test
    void testCalculateTollDuringEvening() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.of(2024, Month.NOVEMBER, 13, 17, 20);
        VehicleDTO vehicle = new VehicleDTO("Car", "XYZ999", dateTime);

        // Act
        double toll = taxRateCalculator.calculate(vehicle);

        // Assert
        assertEquals(13, toll);
    }

    @Test
    void testCalculateTollForVehicleWithExistingTollPass() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.of(2024, Month.NOVEMBER, 13, 6, 27);
        VehicleDTO vehicle = new VehicleDTO("Car", "XYZ999", dateTime);
        TollPass tollPass = new TollPass("XYZ999", dateTime.minusMinutes(3), 13);
        when(tollPassRepository.findAllByPlateNumberAndDateTime(anyString(), any(), any())).thenReturn(List.of(tollPass));

        // Act
        double toll = taxRateCalculator.calculate(vehicle);

        // Assert
        assertEquals(13, toll);
    }

    @Test
    void testCalculateTollForVehicleWithMultipleTollPasses() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.of(2024, Month.NOVEMBER, 13, 14, 35);
        VehicleDTO vehicle = new VehicleDTO("Car", "XYZ999", dateTime);
        TollPass tollPass1 = new TollPass("XYZ999", dateTime.minusMinutes(20), 13);
        TollPass tollPass2 = new TollPass("XYZ999", dateTime.minusMinutes(30), 18);
        when(tollPassRepository.findAllByPlateNumberAndDateTime(anyString(), any(), any())).thenReturn(List.of(tollPass1, tollPass2));

        // Act
        double toll = taxRateCalculator.calculate(vehicle);

        // Assert
        assertEquals(18, toll);
    }

    @Test
    void testCalculateTollForVehicleWithMultipleTollPassesAbove60() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.of(2024, Month.NOVEMBER, 13, 15, 29);
        VehicleDTO vehicle = new VehicleDTO("Car", "XYZ999", dateTime);
        TollPass tollPass1 = new TollPass("XYZ999", dateTime.minusMinutes(10), 65);
        TollPass tollPass2 = new TollPass("XYZ999", dateTime.minusMinutes(15), 75);
        when(tollPassRepository.findAllByPlateNumberAndDateTime(anyString(), any(), any())).thenReturn(List.of(tollPass1, tollPass2));

        // Act
        double toll = taxRateCalculator.calculate(vehicle);

        // Assert
        assertEquals(60, toll);
    }

    @Test
    void testCalculateTollForLateEvening() {
        // Arrange
        LocalDateTime dateTime = LocalDateTime.of(2024, Month.NOVEMBER, 13, 19, 25);
        VehicleDTO vehicle = new VehicleDTO("Car", "XYZ999", dateTime);

        // Act
        double toll = taxRateCalculator.calculate(vehicle);

        // Assert
        assertEquals(0, toll);
    }
}