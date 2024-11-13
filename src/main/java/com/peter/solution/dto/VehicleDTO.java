package com.peter.solution.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public final class VehicleDTO {
    private String type;
    private String plateNumber;
    private LocalDateTime tollPassDateTime;
    private double taxAmount;

    public VehicleDTO(String type, String plateNumber, LocalDateTime tollPassDateTime) {
        this.type = type;
        this.plateNumber = plateNumber;
        this.tollPassDateTime = tollPassDateTime != null ? tollPassDateTime : LocalDateTime.now();
    }

    public VehicleDTO(String type, String plateNumber) {
        this(type, plateNumber, null);
    }

}
