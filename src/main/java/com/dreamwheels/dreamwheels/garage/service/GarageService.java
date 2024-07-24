package com.dreamwheels.dreamwheels.garage.service;

import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.garage.dtos.MotorbikeGarageDto;
import com.dreamwheels.dreamwheels.garage.dtos.VehicleGarageDto;
import com.dreamwheels.dreamwheels.garage.entity.Garage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface GarageService {
    ResponseEntity<GarageApiResponse<Garage>> addVehicleGarage(VehicleGarageDto vehicleGarageDto, HttpServletRequest httpRequest);

    ResponseEntity<GarageApiResponse<Garage>> addMotorbikeGarage(MotorbikeGarageDto motorbikeGarageDto, HttpServletRequest httpServletRequest);

    ResponseEntity<GarageApiResponse<Page<Garage>>> allGarages(int pageNumber);

    ResponseEntity<GarageApiResponse<Garage>> getGarageById(String id);

    ResponseEntity<GarageApiResponse<Garage>> updateVehicleGarage(VehicleGarageDto vehicleGarageDto, String id, HttpServletRequest httpRequest);

    ResponseEntity<GarageApiResponse<Garage>> updateMotorbikeGarage(MotorbikeGarageDto motorbikeGarageDto, String id, HttpServletRequest httpServletRequest);

    ResponseEntity<GarageApiResponse<Page<Garage>>> garagesByCategory(String category, Integer pageNumber);

    ResponseEntity<GarageApiResponse<Page<Garage>>> searchGarages(Integer pageNumber, String query, String vehicleMake, String vehicleModel, Integer mileage, Integer previousOwnersCount, Integer enginePower, Integer topSpeed, Integer acceleration, String transmissionType, String driveTrain, String enginePosition, String engineLayout, String engineAspiration, String bodyType);

    ResponseEntity<GarageApiResponse<Garage>> deleteGarage(String id);

    ResponseEntity<GarageApiResponse<Page<Garage>>> searchMotorbikeGarages(Integer pageNumber, String name, String motorbikeMake, String motorbikeModel, String motorbikeCategory1, Integer mileage, Integer previousOwnersCount, Integer enginePower, Integer topSpeed, Integer acceleration, String transmissionType, String engineAspiration, String engineLayout);
}
