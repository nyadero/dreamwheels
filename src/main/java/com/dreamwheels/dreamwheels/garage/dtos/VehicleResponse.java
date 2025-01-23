package com.dreamwheels.dreamwheels.garage.dtos;

import com.dreamwheels.dreamwheels.garage.enums.*;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class VehicleResponse extends GarageResponse {
    private String vehicleMake;
    private VehicleModel vehicleModel;
    private DriveTrain driveTrain;
    private EngineLayout engineLayout;
    private EnginePosition enginePosition;
    private BodyType bodyType;
}
