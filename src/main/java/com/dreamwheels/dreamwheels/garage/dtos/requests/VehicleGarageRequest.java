package com.dreamwheels.dreamwheels.garage.dtos.requests;

import com.dreamwheels.dreamwheels.configuration.middleware.EnumValidation;
import com.dreamwheels.dreamwheels.garage.enums.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class VehicleGarageRequest {
    @NotEmpty(message = "Vehicle name is required")
    private String name;

    @NotEmpty(message = "Vehicle description is required")
    private String description;

    @NotNull(message = "Buying price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Buying price must be greater than 0")
    private Double buyingPrice;

    @NotNull(message = "Previous owners count is required")
    @Min(value = 0, message = "Previous owners count must be 0 or greater")
    private Integer previousOwnersCount;

    @NotNull(message = "Vehicle acceleration is required")
    @Min(value = 0 ,message = "Vehicle acceleration must be 0 or greater")
    private Integer acceleration;

    @NotNull(message = "Vehicle mileage is required")
    @Min(value = 0 ,message = "Vehicle mileage must be 0 or greater")
    private Integer mileage;

    @NotNull(message = "Vehicle top speed is required")
    @Min(value = 0 ,message = "Vehicle top speed must be 0 or greater")
    private Integer topSpeed;

    @NotNull(message = "Vehicle engine power is required")
    @Min(value = 0 ,message = "Vehicle engine power must be 0 or greater")
    private Integer enginePower;

    @NotNull(message = "Vehicle torque is required")
    @Min(value = 0 ,message = "Vehicle torque must be 0 or greater")
    private Integer torque;

    @NotEmpty(message = "Vehicle make is required")
    @EnumValidation(enumClass = VehicleMake.class, message = "Invalid vehicle make")
    private String vehicleMake;

    @NotEmpty(message = "Vehicle model is required")
    @EnumValidation(enumClass = VehicleModel.class, message = "Invalid vehicle model")
    private String vehicleModel;

    @NotEmpty(message = "Vehicle body type is required")
    @EnumValidation(enumClass = BodyType.class, message = "Invalid body type")
    private String bodyType;


    @NotEmpty(message = "Vehicle fuel type is required")
    @EnumValidation(enumClass = FuelType.class, message = "Invalid fuel type")
    private String fuelType;

    @NotEmpty(message = "Vehicle drive train is required")
    @EnumValidation(enumClass = DriveTrain.class, message = "Invalid drivetrain")
    private String driveTrain;

    @NotEmpty(message = "Vehicle engine layout is required")
    @EnumValidation(enumClass = EngineLayout.class, message = "Invalid engine layout")
    private String engineLayout;

    @NotEmpty(message = "Vehicle engine position is required")
    @EnumValidation(enumClass = EnginePosition.class, message = "Invalid engine position")
    private String enginePosition;

    @NotEmpty(message = "Vehicle engine aspiration is required")
    @EnumValidation(enumClass = EngineAspiration.class, message = "Invalid engine aspiration")
    private String engineAspiration;

    @NotEmpty(message = "Vehicle transmission type is required")
    @EnumValidation(enumClass = TransmissionType.class, message = "Invalid transmission type")
    private String transmissionType;

    // general files
    private List<MultipartFile> generalFiles = new ArrayList<>(0);

    // interior files
    private List<MultipartFile> interiorFiles = new ArrayList<>(0);

    // exterior files
    private List<MultipartFile> exteriorFiles = new ArrayList<>(0);

    // mechanical files
    private List<MultipartFile> mechanicalFiles = new ArrayList<>(0);

    // document files
    private List<MultipartFile> documentFiles = new ArrayList<>(0);

}
