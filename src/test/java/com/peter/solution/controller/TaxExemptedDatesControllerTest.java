package com.peter.solution.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peter.solution.repository.dates.ExemptedDate;
import com.peter.solution.repository.dates.ExemptedDateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaxExemptedDatesController.class)
class TaxExemptedDatesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExemptedDateRepository exemptedDateRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllExemptedDates_ShouldReturnEmptyList_WhenNoExemptedDatesExist() throws Exception {
        when(exemptedDateRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/tax-exempted-dates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllExemptedDates_ShouldReturnListOfExemptedDates() throws Exception {
        ExemptedDate holidayDate = ExemptedDate.ofHolidayDate(1L, LocalDate.of(2023, 12, 25));
        ExemptedDate dayOfWeekDate = ExemptedDate.ofDayOfWeek(2L, DayOfWeek.SATURDAY);
        when(exemptedDateRepository.findAll()).thenReturn(List.of(holidayDate, dayOfWeekDate));

        mockMvc.perform(get("/api/tax-exempted-dates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void saveExemptedDate_ShouldReturnCreatedStatusAndSavedExemptedDate() throws Exception {
        ExemptedDate newHolidayDate = ExemptedDate.ofHolidayDate(null, LocalDate.of(2023, 5, 15));
        ExemptedDate savedHolidayDate = ExemptedDate.ofHolidayDate(1L, LocalDate.of(2023, 5, 15));
        when(exemptedDateRepository.save(Mockito.any(ExemptedDate.class))).thenReturn(savedHolidayDate);

        mockMvc.perform(post("/api/tax-exempted-dates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newHolidayDate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.holidayDate").value("2023-05-15"))
                .andExpect(jsonPath("$.type").value("HOLIDAY_DATE"));

        verify(exemptedDateRepository).save(Mockito.any(ExemptedDate.class));
    }

    @Test
    void deleteExemptedDate_ShouldDeleteExemptedDate_WhenIdExists() throws Exception {
        doNothing().when(exemptedDateRepository).deleteById(anyLong());

        mockMvc.perform(delete("/api/tax-exempted-dates/1"))
                .andExpect(status().isNoContent());

        verify(exemptedDateRepository, times(1)).deleteById(anyLong());
    }

}