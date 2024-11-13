package com.peter.solution.repository.taxrate;

import com.peter.solution.repository.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents a tax rate applicable within a specific time range.
 * This record holds the start and end times for the period during which the tax rate is valid,
 * as well as the corresponding tax amount for that period.
 */
@Data
@NoArgsConstructor
public final class TaxRate implements BaseEntity<Long> {

    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private double amount;

    public TaxRate(Long id, LocalTime startTime, LocalTime endTime, double amount) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
    }

    public TaxRate(LocalTime startTime, LocalTime endTime, double amount) {
        this(null, startTime, endTime, amount);
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxRate taxRate = (TaxRate) o;
        return Objects.equals(id, taxRate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}