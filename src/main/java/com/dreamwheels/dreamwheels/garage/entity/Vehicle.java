package com.dreamwheels.dreamwheels.garage.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "vehicle-garages")
@Builder
public class Vehicle extends Garage {
    private String vehicleMake;
    private String vehicleModel;
    private String vehicleFuelType;
    private String vehicleTransmission;
    private int vehicleMileage;
}
