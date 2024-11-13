package com.peter.solution.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peter.solution.repository.vehicle.TaxExemptedVehicle;
import com.peter.solution.repository.vehicle.TaxExemptedVehicleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaxExemptedVehicleController.class)
class TaxExemptedVehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaxExemptedVehicleRepository taxExemptedVehicleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllExemptedVehicles_ShouldReturnEmptyList_WhenNoVehiclesExist() throws Exception {
        when(taxExemptedVehicleRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/tax-exempted-vehicles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllExemptedVehicles_ShouldReturnListOfVehicles() throws Exception {
        TaxExemptedVehicle vehicle1 = new TaxExemptedVehicle("Emergency Vehicle");
        vehicle1.setId(1L);
        TaxExemptedVehicle vehicle2 = new TaxExemptedVehicle("Diplomatic Vehicle");
        vehicle2.setId(2L);

        when(taxExemptedVehicleRepository.findAll()).thenReturn(List.of(vehicle1, vehicle2));

        mockMvc.perform(get("/api/tax-exempted-vehicles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].vehicle").value("Emergency Vehicle"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].vehicle").value("Diplomatic Vehicle"));
    }

    @Test
    void addExemptedVehicle_ShouldReturnCreatedStatusAndSavedVehicle() throws Exception {
        TaxExemptedVehicle newVehicle = new TaxExemptedVehicle("Military Vehicle");
        TaxExemptedVehicle savedVehicle = new TaxExemptedVehicle("Military Vehicle");
        savedVehicle.setId(3L);

        when(taxExemptedVehicleRepository.save(Mockito.any(TaxExemptedVehicle.class))).thenReturn(savedVehicle);

        mockMvc.perform(post("/api/tax-exempted-vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newVehicle)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.vehicle").value("Military Vehicle"));

        verify(taxExemptedVehicleRepository).save(Mockito.any(TaxExemptedVehicle.class));
    }

    @Test
    void deleteExemptedVehicle_ShouldDeleteVehicle_WhenIdExists() throws Exception {
        doNothing().when(taxExemptedVehicleRepository).deleteById(anyLong());

        mockMvc.perform(delete("/api/tax-exempted-vehicles/1"))
                .andExpect(status().isNoContent());

        verify(taxExemptedVehicleRepository).deleteById(1L);
    }

}