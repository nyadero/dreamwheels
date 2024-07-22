package com.dreamwheels.dreamwheels.garage.serviceimpl;

import com.dreamwheels.dreamwheels.auth.repository.AuthRepository;
import com.dreamwheels.dreamwheels.configuration.exceptions.EntityNotFoundException;
import com.dreamwheels.dreamwheels.configuration.middleware.TryCatchAnnotation;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import com.dreamwheels.dreamwheels.garage.dtos.MotorbikeGarageDto;
import com.dreamwheels.dreamwheels.garage.dtos.VehicleGarageDto;
import com.dreamwheels.dreamwheels.garage.entity.Garage;
import com.dreamwheels.dreamwheels.garage.entity.Motorbike;
import com.dreamwheels.dreamwheels.garage.entity.Vehicle;
import com.dreamwheels.dreamwheels.garage.enums.*;
import com.dreamwheels.dreamwheels.garage.repository.GarageRepository;
import com.dreamwheels.dreamwheels.garage.service.GarageService;
import com.dreamwheels.dreamwheels.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GarageServiceImpl implements GarageService {
    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private AuthRepository authRepository;

    public static final int PAGE_SIZE = 20;

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> addVehicleGarage(VehicleGarageDto vehicleGarageDto) {
        Garage garage = null;
        garage = newVehiclegarage(vehicleGarageDto);
        garage.setName(vehicleGarageDto.getName());
        garage.setDescription(vehicleGarageDto.getDescription());
        garage.setBuyingPrice(vehicleGarageDto.getBuyingPrice());
        garage.setPreviousOwnersCount(vehicleGarageDto.getPreviousOwnersCount());
        garage.setAcceleration(vehicleGarageDto.getAcceleration());
        garage.setMileage(vehicleGarageDto.getMileage());
        garage.setEnginePower(vehicleGarageDto.getEnginePower());
        garage.setEngineAspiration(EngineAspiration.valueOf(vehicleGarageDto.getEngineAspiration()));
        garage.setFuelType(FuelType.valueOf(vehicleGarageDto.getFuelType()));
        garage.setTorque(vehicleGarageDto.getTorque());
        garage.setTopSpeed(vehicleGarageDto.getTopSpeed());
        garage.setTransmissionType(TransmissionType.valueOf(vehicleGarageDto.getTransmissionType()));
        garage.setUser(authenticatedUser());

        return new ResponseEntity<>(new GarageApiResponse(garageRepository.save(garage), "Your vehicle garage has been saved", ResponseType.SUCCESS),
                HttpStatus.CREATED);
    }


    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> addMotorbikeGarage(
            MotorbikeGarageDto motorbikeGarageDto
    ) {
        Garage garage = null;
        garage = newMotorbikegarage(motorbikeGarageDto);
        garage.setName(motorbikeGarageDto.getName());
        garage.setDescription(motorbikeGarageDto.getDescription());
        garage.setBuyingPrice(motorbikeGarageDto.getBuyingPrice());
        garage.setPreviousOwnersCount(motorbikeGarageDto.getPreviousOwnersCount());
        garage.setAcceleration(motorbikeGarageDto.getAcceleration());
        garage.setMileage(motorbikeGarageDto.getMileage());
        garage.setEnginePower(motorbikeGarageDto.getEnginePower());
        garage.setEngineAspiration(EngineAspiration.valueOf(motorbikeGarageDto.getEngineAspiration()));
        garage.setFuelType(FuelType.valueOf(motorbikeGarageDto.getFuelType()));
        garage.setTorque(motorbikeGarageDto.getTorque());
        garage.setTopSpeed(motorbikeGarageDto.getTopSpeed());
        garage.setTransmissionType(TransmissionType.valueOf(motorbikeGarageDto.getTransmissionType()));
        garage.setUser(authenticatedUser());

        return new ResponseEntity<>(new GarageApiResponse(garageRepository.save(garage), "Your motorbike garage has been saved", ResponseType.SUCCESS),
                HttpStatus.CREATED);
    }


    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> allGarages(int pageNumber) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE, sort);
        Page<Garage> garagesPage = garageRepository.findAll(pageRequest);
        return new ResponseEntity<>(new GarageApiResponse(garagesPage, "Found " + garagesPage.getTotalElements() + " garages", ResponseType.SUCCESS), HttpStatus.OK);
    }

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> getGarageById(String id) {
        Garage garage = garageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Garage not found"));
        return new ResponseEntity<>(new GarageApiResponse(garage, "Found garage", ResponseType.SUCCESS), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GarageApiResponse> updateVehicleGarage(VehicleGarageDto vehicleGarageDto, String id) {
        Garage garage = garageRepository.findByIdAndUserId(id, authenticatedUser().getId()).orElseThrow(() -> new EntityNotFoundException("Garage not found"));
        if (garage instanceof Vehicle vehicle){

//            vehicle.setVehicleMake(vehicleGarageDto.getVehicleMake());
//            vehicle.setVehicleModel(vehicleGarageDto.getVehicleModel());
//            vehicle.setVehicleFuelType(vehicleGarageDto.getVehicleFuelType());
//            vehicle.setVehicleTransmission(vehicleGarageDto.getVehicleTransmission());
            vehicle.setName(vehicleGarageDto.getName());
            vehicle.setDescription(vehicleGarageDto.getDescription());
            vehicle.setBuyingPrice(vehicleGarageDto.getBuyingPrice());
            vehicle.setPreviousOwnersCount(vehicleGarageDto.getPreviousOwnersCount());

            garageRepository.save(vehicle);
        }

        return new ResponseEntity<>(new GarageApiResponse(null, "Vehicle garage updated successfully", ResponseType.SUCCESS), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GarageApiResponse> updateMotorbikeGarage(MotorbikeGarageDto motorbikeGarageDto, String id) {
        Garage garage = garageRepository.findByIdAndUserId(id, authenticatedUser().getId()).orElseThrow(() -> new EntityNotFoundException("Garage not found"));
        if (garage instanceof Motorbike motorbike){
            motorbike.setName(motorbikeGarageDto.getName());
            motorbike.setDescription(motorbikeGarageDto.getDescription());
            motorbike.setBuyingPrice(motorbikeGarageDto.getBuyingPrice());
            motorbike.setPreviousOwnersCount(motorbikeGarageDto.getPreviousOwnersCount());
//            motorbike.setMotorbikeMileage(String.valueOf(motorbikeGarageDto.getMotorbikeMileage()));
//            motorbike.setMotorbikeMake(motorbikeGarageDto.getMotorbikeMake());
//            motorbike.setMotorbikeModel(motorbikeGarageDto.getMotorbikeModel());
//            motorbike.setMotorbikeFuelType(motorbikeGarageDto.getMotorbikeFuelType());
//            motorbike.setMotorbikeTransmission(motorbikeGarageDto.getMotorbikeTransmission());

            garageRepository.save(motorbike);
        }
        return new ResponseEntity<>(new GarageApiResponse(null, "Motorbike garage updated successfully",ResponseType.SUCCESS), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GarageApiResponse> garagesByCategory(String category) {
        return null;
    }

    @Override
    public ResponseEntity<GarageApiResponse> searchGarages(String query) {
        return null;
    }

    @Override
    public ResponseEntity<GarageApiResponse> searchMotorbikeGarages() {
        return null;
    }


    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> deleteGarage(String id) {
        Garage garage = garageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Garage not found"));
        garageRepository.deleteById(garage.getId());
        return new ResponseEntity<>(new GarageApiResponse(null, "garage deleted",ResponseType.SUCCESS), HttpStatus.OK);
    }



    private Garage newVehiclegarage(VehicleGarageDto vehicleGarageDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleMake(VehicleMake.fromString(vehicleGarageDto.getVehicleMake()).getDisplayName());
        vehicle.setVehicleModel(VehicleModel.valueOf(vehicleGarageDto.getVehicleModel()));
        vehicle.setDriveTrain(DriveTrain.valueOf(vehicleGarageDto.getDriveTrain()));
        vehicle.setBodyType(BodyType.valueOf(vehicleGarageDto.getBodyType()));
        vehicle.setEngineLayout(EngineLayout.valueOf(vehicleGarageDto.getEngineLayout()));
        vehicle.setEnginePosition(EnginePosition.valueOf(vehicleGarageDto.getEnginePosition()));
        return vehicle;
    }

    private Garage newMotorbikegarage(MotorbikeGarageDto motorbikeGarageDto) {
        Motorbike motorbike = new Motorbike();
        motorbike.setMotorbikeMake(MotorbikeMake.valueOf(motorbikeGarageDto.getMotorbikeMake()));
        motorbike.setMotorbikeModel(MotorbikeModel.valueOf(motorbikeGarageDto.getMotorbikeModel()));
        motorbike.setMotorbikeCategory(MotorbikeCategory.valueOf(motorbikeGarageDto.getMotorbikeCategory()));
        return motorbike;
    }

    private User authenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authRepository.findByEmail(authentication.getName());
    }


}
