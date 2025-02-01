package com.dreamwheels.dreamwheels.garage.adapters;

import com.dreamwheels.dreamwheels.comments.adapters.CommentAdapter;
import com.dreamwheels.dreamwheels.configuration.adapters.EntityAdapter;
import com.dreamwheels.dreamwheels.garage.dtos.responses.GarageResponse;
import com.dreamwheels.dreamwheels.garage.dtos.responses.MotorbikeResponse;
import com.dreamwheels.dreamwheels.garage.dtos.responses.VehicleResponse;
import com.dreamwheels.dreamwheels.garage.entity.Garage;
import com.dreamwheels.dreamwheels.garage.entity.Motorbike;
import com.dreamwheels.dreamwheels.garage.entity.Vehicle;
import com.dreamwheels.dreamwheels.uploaded_files.adapters.UploadedFileAdapter;
import com.dreamwheels.dreamwheels.users.dtos.UserDto;
import com.dreamwheels.dreamwheels.users.entity.User;
import org.springframework.stereotype.Component;

@Component
public class GarageAdapter implements EntityAdapter<Garage, GarageResponse> {

    private final UploadedFileAdapter uploadedFileAdapter;

    private final CommentAdapter commentAdapter;

    public GarageAdapter(UploadedFileAdapter uploadedFileAdapter, CommentAdapter commentAdapter) {
        this.uploadedFileAdapter = uploadedFileAdapter;
        this.commentAdapter = commentAdapter;
    }

    @Override
    public GarageResponse toBusiness(Garage garage) {
        GarageResponse garageResponse = null;
        if (garage instanceof Vehicle vehicle){
            garageResponse = vehicleGarageDto(vehicle);
            buildGarageDto(garageResponse, garage);
        } else if (garage instanceof Motorbike motorbike) {
            garageResponse = motorbikeGarageDto(motorbike);
            buildGarageDto(garageResponse, garage);
        }else{
            return null;
        }
        return garageResponse;
    }

    private GarageResponse motorbikeGarageDto(Motorbike motorbike) {
        return MotorbikeResponse.builder()
                .motorbikeCategory(motorbike.getMotorbikeCategory())
                .motorbikeMake(motorbike.getMotorbikeMake())
                .motorbikeModel(motorbike.getMotorbikeModel())
                .build();
    }


    private GarageResponse vehicleGarageDto(Vehicle vehicle) {
        return VehicleResponse.builder()
                .vehicleModel(vehicle.getVehicleModel())
                .bodyType(vehicle.getBodyType())
                .driveTrain(vehicle.getDriveTrain())
                .engineLayout(vehicle.getEngineLayout())
                .enginePosition(vehicle.getEnginePosition())
                .vehicleMake(vehicle.getVehicleMake())
                .build();
    }

    private void buildGarageDto(GarageResponse garageResponse, Garage garage) {
        garageResponse.setAcceleration(garage.getAcceleration());
        garageResponse.setId(garage.getId());
        garageResponse.setCategory(garage.getCategory());
        garageResponse.setDescription(garage.getDescription());
        garageResponse.setBuyingPrice(garage.getBuyingPrice());
        garageResponse.setEnginePower(garage.getEnginePower());
        garageResponse.setCommentsCount(garage.getCommentsCount());
        garageResponse.setName(garage.getName());
        garageResponse.setMileage(garage.getMileage());
        garageResponse.setEngineAspiration(garage.getEngineAspiration());
        garageResponse.setLikesCount(garage.getLikesCount());
        garageResponse.setTorque(garage.getTorque());
        garageResponse.setFuelType(garage.getFuelType());
        garageResponse.setTransmissionType(garage.getTransmissionType());
        garageResponse.setCreatedAt(garage.getCreatedAt());
        garageResponse.setUpdatedAt(garage.getUpdatedAt());
        garageResponse.setUser(garage.getUser() != null ? mapToUserDto(garage.getUser()) : null);
        garageResponse.setGarageFiles(garage.getGarageFiles().stream().map(uploadedFileAdapter::toBusiness).toList());
        garageResponse.setPreviousOwnersCount(garage.getPreviousOwnersCount());
        garageResponse.setComments(garage.getComments().stream().map(commentAdapter::toBusiness).toList());
    }

    private UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
