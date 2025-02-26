package com.dreamwheels.dreamwheels.garage.adapters;

import com.dreamwheels.dreamwheels.garage.dtos.responses.GarageResponse;
import com.dreamwheels.dreamwheels.garage.dtos.responses.MotorbikeResponse;
import com.dreamwheels.dreamwheels.garage.dtos.responses.VehicleResponse;
import com.dreamwheels.dreamwheels.garage.entity.Garage;
import com.dreamwheels.dreamwheels.garage.entity.Motorbike;
import com.dreamwheels.dreamwheels.garage.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GarageMapper {
    GarageMapper INSTANCE = Mappers.getMapper(GarageMapper.class);

    GarageResponse toGarageDTO(Garage garage);

    MotorbikeResponse toMotorbikeDTO(Motorbike motorbike);

    VehicleResponse toVehicleDTO(Vehicle vehicle);

//    List<GarageDTO> toGarageDTOList(List<Garage> garages);

//    Garage toGarageEntity(GarageDTO garageDTO);
//
//    Motorbike toMotorbikeEntity(MotorbikeDTO motorbikeDTO);
//
//    Vehicle toVehicleEntity(VehicleDTO vehicleDTO);

}