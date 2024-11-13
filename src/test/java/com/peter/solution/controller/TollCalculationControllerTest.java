package com.peter.solution.controller;

import com.peter.solution.dto.VehicleDTO;
import com.peter.solution.service.TaxRateCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TollCalculationController.class)
class TollCalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaxRateCalculator taxRateCalculator;

    @Test
    void testTaxRageCalculator() throws Exception {
        String vehicleJson = "{\"plateNumber\":\"ABC123\",\"type\":\"Car\",\"tollPassDateTime\":\"2024-11-13T10:00:00\"}";
        when(taxRateCalculator.calculate(new VehicleDTO("Car", "ABC123", LocalDateTime.of(2024, 11, 13, 10, 0)))).thenReturn(20.0);

        mockMvc.perform(post("/api/toll/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taxAmount").value(20.0));
    }
}