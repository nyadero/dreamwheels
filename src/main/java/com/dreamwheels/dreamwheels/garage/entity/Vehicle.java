package com.dreamwheels.dreamwheels.garage.entity;

import com.dreamwheels.dreamwheels.garage.enums.DriveTrain;
import com.dreamwheels.dreamwheels.garage.enums.EngineAspiration;
import com.dreamwheels.dreamwheels.garage.enums.EngineLayout;
import com.dreamwheels.dreamwheels.garage.enums.EnginePosition;
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
    private String vehicleModel;
    @Enumerated(value = EnumType.STRING)
    private DriveTrain driveTrain;
    @Enumerated(value = EnumType.STRING)
    private EngineLayout engineLayout;
    @Enumerated(value = EnumType.STRING)
    private EnginePosition enginePosition;
    @Enumerated(value = EnumType.STRING)
    private EngineAspiration vehicleEngineAspiration;
}
