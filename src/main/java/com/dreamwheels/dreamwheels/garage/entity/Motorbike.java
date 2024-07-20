package com.dreamwheels.dreamwheels.garage.entity;

import com.dreamwheels.dreamwheels.garage.enums.EngineAspiration;
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
    private String motorbikeMake;
    private String motorbikeModel;
    @Enumerated(EnumType.STRING)
    private EngineAspiration motorbikeEngineAspiration;
}
