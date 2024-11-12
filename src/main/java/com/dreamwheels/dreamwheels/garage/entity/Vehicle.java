package com.dreamwheels.dreamwheels.garage.entity;

import com.dreamwheels.dreamwheels.garage.enums.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "vehicle_garages")
@Builder
public class Vehicle extends Garage {
    private String vehicleMake;
    @Enumerated(EnumType.STRING)
    private VehicleModel vehicleModel;
    @Enumerated(value = EnumType.STRING)
    private DriveTrain driveTrain;
    @Enumerated(value = EnumType.STRING)
    private EngineLayout engineLayout;
    @Enumerated(value = EnumType.STRING)
    private EnginePosition enginePosition;
    @Enumerated(value = EnumType.STRING)
    private BodyType bodyType;
}
