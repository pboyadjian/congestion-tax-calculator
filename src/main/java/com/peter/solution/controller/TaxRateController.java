package com.peter.solution.controller;

import com.peter.solution.repository.taxrate.TaxRate;
import com.peter.solution.repository.taxrate.TaxRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tax-rates")
@RequiredArgsConstructor
public class TaxRateController {

    private final TaxRateRepository taxRateRepository;

    /**
     * Retrieves all tax rates.
     *
     * @return a list of all tax rates
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaxRate> getAllTaxRates() {
        return taxRateRepository.findAll();
    }

    /**
     * Creates a new tax rate.
     *
     * @param taxRate the tax rate to be created
     * @return the created tax rate
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TaxRate createTaxRate(@RequestBody TaxRate taxRate) {
        return taxRateRepository.save(taxRate);
    }

    /**
     * Deletes a tax rate by its ID.
     *
     * @param id the ID of the tax rate to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaxRate(@PathVariable Long id) {
        taxRateRepository.deleteById(id);
    }

}