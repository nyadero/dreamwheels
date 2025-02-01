package com.dreamwheels.dreamwheels.garage.dtos.responses;

import com.dreamwheels.dreamwheels.garage.enums.MotorbikeCategory;
import com.dreamwheels.dreamwheels.garage.enums.MotorbikeMake;
import com.dreamwheels.dreamwheels.garage.enums.MotorbikeModel;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class MotorbikeResponse extends GarageResponse {
    private MotorbikeMake motorbikeMake;

    private MotorbikeModel motorbikeModel;

    private MotorbikeCategory motorbikeCategory;
}
