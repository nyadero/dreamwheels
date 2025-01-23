package com.dreamwheels.dreamwheels.garage.service;

import com.dreamwheels.dreamwheels.configuration.responses.CustomPageResponse;
import com.dreamwheels.dreamwheels.garage.dtos.GarageResponse;
import com.dreamwheels.dreamwheels.garage.dtos.requests.MotorbikeGarageRequest;
import com.dreamwheels.dreamwheels.garage.dtos.requests.VehicleGarageRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface GarageService {
    GarageResponse addVehicleGarage(VehicleGarageRequest vehicleGarageDto, HttpServletRequest httpRequest);

    GarageResponse addMotorbikeGarage(MotorbikeGarageRequest motorbikeGarageDto, HttpServletRequest httpServletRequest);

    CustomPageResponse<GarageResponse> allGarages(int pageNumber);

    GarageResponse getGarageById(String id);

    GarageResponse updateVehicleGarage(VehicleGarageRequest vehicleGarageDto, String id, HttpServletRequest httpRequest);

    GarageResponse updateMotorbikeGarage(MotorbikeGarageRequest motorbikeGarageDto, String id, HttpServletRequest httpServletRequest);

    CustomPageResponse<GarageResponse> garagesByCategory(String category, Integer pageNumber);

    CustomPageResponse<GarageResponse> searchGarages(Integer pageNumber, String query, String vehicleMake, String vehicleModel, Integer mileage, Integer previousOwnersCount, Integer enginePower, Integer topSpeed, Integer acceleration, String transmissionType, String driveTrain, String enginePosition, String engineLayout, String engineAspiration, String bodyType);

    void deleteGarage(String id);

    CustomPageResponse<GarageResponse> searchMotorbikeGarages(Integer pageNumber, String name, String motorbikeMake, String motorbikeModel, String motorbikeCategory1, Integer mileage, Integer previousOwnersCount, Integer enginePower, Integer topSpeed, Integer acceleration, String transmissionType, String engineAspiration, String engineLayout);
}
