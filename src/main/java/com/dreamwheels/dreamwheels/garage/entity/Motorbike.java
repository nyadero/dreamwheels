package com.dreamwheels.dreamwheels.garage.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "motorbike-wheelies")
public class Motorbike extends Garage {
    private String motorbikeMake;
    private String motorbikeModel;
    private String motorbikeFuelType;
    private String motorbikeTransmission;
    private String motorbikeMileage;
}
