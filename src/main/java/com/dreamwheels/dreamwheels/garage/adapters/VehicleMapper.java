package com.dreamwheels.dreamwheels.garage.adapters;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleMapper vehicleMapper = Mappers.getMapper(VehicleMapper.class);
}
