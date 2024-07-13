package com.dreamwheels.dreamwheels.garage.service;

import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.garage.dtos.MotorbikeGarageDto;
import com.dreamwheels.dreamwheels.garage.dtos.VehicleGarageDto;
import org.springframework.http.ResponseEntity;

public interface GarageService {
    ResponseEntity<GarageApiResponse> addVehicleGarage(VehicleGarageDto vehicleGarageDto);

    ResponseEntity<GarageApiResponse> addMotorbikeGarage(MotorbikeGarageDto motorbikeGarageDto);

    ResponseEntity<GarageApiResponse> allGarages(int pageNumber);

    ResponseEntity<GarageApiResponse> getGarageById(String id);

    ResponseEntity<GarageApiResponse> updateVehicleGarage(VehicleGarageDto vehicleGarageDto, String id);

    ResponseEntity<GarageApiResponse> updateMotorbikeGarage(MotorbikeGarageDto motorbikeGarageDto, String id);

    ResponseEntity<GarageApiResponse> garagesByCategory(String category);

    ResponseEntity<GarageApiResponse> searchGarages(String query);

    ResponseEntity<GarageApiResponse> deleteGarage(String id);


}
