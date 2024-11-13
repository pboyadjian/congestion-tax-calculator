package com.peter.solution.repository.taxrate;

import com.peter.solution.repository.InMemoryRepository;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * Repository class for managing tax rates applied during different time intervals.
 * <p>
 * This class extends {@link InMemoryRepository} to leverage basic CRUD operations for storing
 * and retrieving {@link TaxRate} entities. Each tax rate represents a specific time range and
 * the corresponding toll amount that should be applied during that range.
 * <p>
 * The constructor pre-populates the repository with a set of predefined tax rates that
 * define toll amounts for various time periods throughout the day.
 */
@Component
public class TaxRateRepository extends InMemoryRepository<TaxRate> {
    public TaxRateRepository() {
        save(new TaxRate(LocalTime.of(6, 0), LocalTime.of(6, 29), 8));
        save(new TaxRate(LocalTime.of(6, 30), LocalTime.of(6, 59), 13));
        save(new TaxRate(LocalTime.of(7, 0), LocalTime.of(7, 59), 18));
        save(new TaxRate(LocalTime.of(8, 0), LocalTime.of(8, 29), 13));
        save(new TaxRate(LocalTime.of(8, 30), LocalTime.of(14, 59), 8));
        save(new TaxRate(LocalTime.of(15, 0), LocalTime.of(15, 29), 13));
        save(new TaxRate(LocalTime.of(15, 30), LocalTime.of(16, 59), 18));
        save(new TaxRate(LocalTime.of(17, 0), LocalTime.of(17, 59), 13));
        save(new TaxRate(LocalTime.of(18, 0), LocalTime.of(18, 29), 8));
        save(new TaxRate(LocalTime.of(18, 30), LocalTime.of(5, 59), 0));
    }
}