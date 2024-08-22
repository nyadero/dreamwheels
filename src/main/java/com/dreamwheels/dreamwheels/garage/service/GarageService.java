package com.dreamwheels.dreamwheels.garage.service;

import com.dreamwheels.dreamwheels.garage.dtos.MotorbikeGarageDto;
import com.dreamwheels.dreamwheels.garage.dtos.VehicleGarageDto;
import com.dreamwheels.dreamwheels.garage.entity.Garage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface GarageService {
    Garage addVehicleGarage(VehicleGarageDto vehicleGarageDto, HttpServletRequest httpRequest);

    Garage addMotorbikeGarage(MotorbikeGarageDto motorbikeGarageDto, HttpServletRequest httpServletRequest);

    Page<Garage> allGarages(int pageNumber);

    Garage getGarageById(String id);

    Garage updateVehicleGarage(VehicleGarageDto vehicleGarageDto, String id, HttpServletRequest httpRequest);

    Garage updateMotorbikeGarage(MotorbikeGarageDto motorbikeGarageDto, String id, HttpServletRequest httpServletRequest);

    Page<Garage> garagesByCategory(String category, Integer pageNumber);

    Page<Garage> searchGarages(Integer pageNumber, String query, String vehicleMake, String vehicleModel, Integer mileage, Integer previousOwnersCount, Integer enginePower, Integer topSpeed, Integer acceleration, String transmissionType, String driveTrain, String enginePosition, String engineLayout, String engineAspiration, String bodyType);

    void deleteGarage(String id);

    Page<Garage> searchMotorbikeGarages(Integer pageNumber, String name, String motorbikeMake, String motorbikeModel, String motorbikeCategory1, Integer mileage, Integer previousOwnersCount, Integer enginePower, Integer topSpeed, Integer acceleration, String transmissionType, String engineAspiration, String engineLayout);
}
