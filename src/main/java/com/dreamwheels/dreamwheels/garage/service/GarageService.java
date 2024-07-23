package com.dreamwheels.dreamwheels.garage.service;

import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.garage.dtos.MotorbikeGarageDto;
import com.dreamwheels.dreamwheels.garage.dtos.VehicleGarageDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface GarageService {
    ResponseEntity<GarageApiResponse> addVehicleGarage(VehicleGarageDto vehicleGarageDto, HttpServletRequest httpRequest);

    ResponseEntity<GarageApiResponse> addMotorbikeGarage(MotorbikeGarageDto motorbikeGarageDto, HttpServletRequest httpServletRequest);

    ResponseEntity<GarageApiResponse> allGarages(int pageNumber);

    ResponseEntity<GarageApiResponse> getGarageById(String id);

    ResponseEntity<GarageApiResponse> updateVehicleGarage(VehicleGarageDto vehicleGarageDto, String id, HttpServletRequest httpRequest);

    ResponseEntity<GarageApiResponse> updateMotorbikeGarage(MotorbikeGarageDto motorbikeGarageDto, String id, HttpServletRequest httpServletRequest);

    ResponseEntity<GarageApiResponse> garagesByCategory(String category, Integer pageNumber);

    ResponseEntity<GarageApiResponse> searchGarages(Integer pageNumber, String query, String vehicleMake, String vehicleModel, Integer mileage, Integer previousOwnersCount, Integer enginePower, Integer topSpeed, Integer acceleration, String transmissionType, String driveTrain, String enginePosition, String engineLayout, String engineAspiration, String bodyType);

    ResponseEntity<GarageApiResponse> deleteGarage(String id);


    ResponseEntity<GarageApiResponse> searchMotorbikeGarages();

    ResponseEntity<GarageApiResponse> searchMotorbikeGarages(Integer pageNumber, String name, String motorbikeMake, String motorbikeModel, String motorbikeCategory1, Integer mileage, Integer previousOwnersCount, Integer enginePower, Integer topSpeed, Integer acceleration, String transmissionType, String engineAspiration, String engineLayout);
}
