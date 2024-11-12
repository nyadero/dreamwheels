package com.dreamwheels.dreamwheels.garage.dtos;

import com.dreamwheels.dreamwheels.garage.enums.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class VehicleDto extends GarageDto{
    private String vehicleMake;
    private VehicleModel vehicleModel;
    private DriveTrain driveTrain;
    private EngineLayout engineLayout;
    private EnginePosition enginePosition;
    private BodyType bodyType;
}
