package com.peter.solution.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peter.solution.repository.taxrate.TaxRate;
import com.peter.solution.repository.taxrate.TaxRateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaxRateController.class)
class TaxRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaxRateRepository taxRateRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllTaxRates_ShouldReturnEmptyList_WhenNoTaxRatesExist() throws Exception {
        when(taxRateRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/tax-rates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllTaxRates_ShouldReturnListOfTaxRates() throws Exception {
        TaxRate rate1 = new TaxRate(1L, LocalTime.of(8, 0), LocalTime.of(10, 0), 10.0);
        TaxRate rate2 = new TaxRate(2L, LocalTime.of(10, 0), LocalTime.of(12, 0), 15.0);
        when(taxRateRepository.findAll()).thenReturn(List.of(rate1, rate2));

        mockMvc.perform(get("/api/tax-rates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].startTime").value("08:00:00"))
                .andExpect(jsonPath("$[0].endTime").value("10:00:00"))
                .andExpect(jsonPath("$[0].amount").value(10.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].startTime").value("10:00:00"))
                .andExpect(jsonPath("$[1].endTime").value("12:00:00"))
                .andExpect(jsonPath("$[1].amount").value(15.0));
    }

    @Test
    void createTaxRate_ShouldReturnCreatedStatusAndSavedTaxRate() throws Exception {
        TaxRate newRate = new TaxRate(1L, LocalTime.of(14, 0), LocalTime.of(16, 0), 20.0);
        TaxRate savedRate = new TaxRate(3L, LocalTime.of(14, 0), LocalTime.of(16, 0), 20.0);
        when(taxRateRepository.save(Mockito.any(TaxRate.class))).thenReturn(savedRate);

        mockMvc.perform(post("/api/tax-rates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.startTime").value("14:00:00"))
                .andExpect(jsonPath("$.endTime").value("16:00:00"))
                .andExpect(jsonPath("$.amount").value(20.0));

        verify(taxRateRepository).save(Mockito.any(TaxRate.class));
    }

    @Test
    void deleteTaxRate_ShouldDeleteTaxRate_WhenIdExists() throws Exception {
        doNothing().when(taxRateRepository).deleteById(anyLong());

        mockMvc.perform(delete("/api/tax-rates/1"))
                .andExpect(status().isNoContent());

        verify(taxRateRepository).deleteById(1L);
    }

}