package com.dreamwheels.dreamwheels.garage.adapters;

import com.dreamwheels.dreamwheels.garage.dtos.responses.GarageResponse;
import com.dreamwheels.dreamwheels.garage.dtos.responses.MotorbikeResponse;
import com.dreamwheels.dreamwheels.garage.dtos.responses.VehicleResponse;
import com.dreamwheels.dreamwheels.garage.entity.Garage;
import com.dreamwheels.dreamwheels.garage.entity.Motorbike;
import com.dreamwheels.dreamwheels.garage.entity.Vehicle;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GarageMapper {

    // Automatically detect subclass and return the correct response type
    default GarageResponse toGarageDto(Garage garage){
        if (garage instanceof Motorbike){
            return toMotorbikeResponse((Motorbike) garage);
        } else if (garage instanceof Vehicle) {
            return toVehicleResponse((Vehicle) garage);
        }
        return toGarageResponse(garage);
    }

    GarageResponse toGarageResponse(Garage garage);

    @InheritConfiguration(name = "toGarageResponse")
    MotorbikeResponse toMotorbikeResponse(Motorbike motorbike);

    @InheritConfiguration(name = "toGarageResponse")
    VehicleResponse toVehicleResponse(Vehicle vehicle);

}