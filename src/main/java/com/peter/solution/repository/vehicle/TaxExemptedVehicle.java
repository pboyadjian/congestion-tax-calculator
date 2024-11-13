package com.peter.solution.repository.vehicle;

import com.peter.solution.repository.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaxExemptedVehicle implements BaseEntity<Long> {
    private Long id;
    private String vehicle;

    public TaxExemptedVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public static TaxExemptedVehicle ofVehicle(String vehicle) {
        return new TaxExemptedVehicle(vehicle);
    }

    public void setId(Long id) {
        this.id = id;
    }
}
