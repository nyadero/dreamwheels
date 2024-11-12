package com.dreamwheels.dreamwheels.garage.dtos;

import com.dreamwheels.dreamwheels.garage.enums.MotorbikeCategory;
import com.dreamwheels.dreamwheels.garage.enums.MotorbikeMake;
import com.dreamwheels.dreamwheels.garage.enums.MotorbikeModel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class MotorbikeDto extends GarageDto{
    private MotorbikeMake motorbikeMake;

    private MotorbikeModel motorbikeModel;

    private MotorbikeCategory motorbikeCategory;
}
