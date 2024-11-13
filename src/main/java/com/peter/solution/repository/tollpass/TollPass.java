package com.peter.solution.repository.tollpass;

import com.peter.solution.repository.BaseEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a toll pass record for a vehicle.
 * This record holds the vehicle's plate number, the date and time the toll pass was recorded,
 * and the toll amount for that pass.
 */
@Data
@RequiredArgsConstructor
public final class TollPass implements BaseEntity<Long> {
    private Long id;
    private final String plateNumber;
    private final LocalDateTime passDateTime;
    private final double tollAmount;

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TollPass tollPass = (TollPass) o;
        return Objects.equals(id, tollPass.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}