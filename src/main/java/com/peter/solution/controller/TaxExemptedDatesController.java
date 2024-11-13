package com.peter.solution.controller;

import com.peter.solution.repository.dates.ExemptedDate;
import com.peter.solution.repository.dates.ExemptedDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing exempted dates.
 */
@RestController
@RequestMapping("/api/tax-exempted-dates")
@RequiredArgsConstructor
public class TaxExemptedDatesController {

    private final ExemptedDateRepository exemptedDateRepository;

    /**
     * Retrieves all exempted dates.
     *
     * @return the list of all exempted dates
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExemptedDate> getAllExemptedDates() {
        return exemptedDateRepository.findAll();
    }

    /**
     * Saves a new exempted date entry.
     *
     * @param exemptedDate the exempted date entry to save
     * @return the saved exempted date
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ExemptedDate saveExemptedDate(@RequestBody ExemptedDate exemptedDate) {
        return exemptedDateRepository.save(exemptedDate);
    }

    /**
     * Deletes an exempted date by its unique identifier.
     *
     * @param id the unique identifier of the exempted date to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExemptedDate(@PathVariable Long id) {
        exemptedDateRepository.deleteById(id);
    }
}