package com.dreamwheels.dreamwheels.garage.entity;

import com.dreamwheels.dreamwheels.garage.enums.EngineAspiration;
import com.dreamwheels.dreamwheels.garage.enums.MotorbikeCategory;
import com.dreamwheels.dreamwheels.garage.enums.MotorbikeMake;
import com.dreamwheels.dreamwheels.garage.enums.MotorbikeModel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "motorbike_garages")
public class Motorbike extends Garage {
    @Enumerated(EnumType.STRING)
    private MotorbikeMake motorbikeMake;

    @Enumerated(EnumType.STRING)
    private MotorbikeModel motorbikeModel;

    @Enumerated(EnumType.STRING)
    private MotorbikeCategory motorbikeCategory;
}
