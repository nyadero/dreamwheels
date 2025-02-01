package com.dreamwheels.dreamwheels.garage.adapters;

import com.dreamwheels.dreamwheels.garage.dtos.responses.GarageResponse;
import com.dreamwheels.dreamwheels.garage.entity.Garage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GarageMapper {
    GarageResponse toDto(Garage garage);
}
