package com.dreamwheels.dreamwheels.garage.dtos;

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
public class MotorbikeGarageDto {
    @NotEmpty(message = "Motorbike name is required")
    private String name;

    @NotEmpty(message = "motorbike description is required")
    private String description;

    @NotNull(message = "Buying price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Buying price must be greater than 0")
    private Double buyingPrice;

    @NotNull(message = "Previous owners count is required")
    @Min(value = 0, message = "Previous owners count must be 0 or greater")
    private Integer previousOwnersCount;

    @NotNull(message = "Motorbike acceleration is required")
    @Min(value = 0 ,message = "Motorbike acceleration must be 0 or greater")
    private Integer acceleration;

    @NotNull(message = "Motorbike mileage is required")
    @Min(value = 0 ,message = "Motorbike mileage must be 0 or greater")
    private Integer mileage;

    @NotNull(message = "Motorbike top speed is required")
    @Min(value = 0 ,message = "Motorbike top speed must be 0 or greater")
    private Integer topSpeed;

    @NotNull(message = "Motorbike engine power is required")
    @Min(value = 0 ,message = "Motorbike engine power must be 0 or greater")
    private Integer enginePower;

    @NotNull(message = "Motorbike torque is required")
    @Min(value = 0 ,message = "Motorbike torque must be 0 or greater")
    private Integer torque;

    @NotEmpty(message = "motorbike make is required")
    @EnumValidation(enumClass = MotorbikeMake.class, message = "Invalid motorbike make")
    private String motorbikeMake;

    @NotEmpty(message = "motorbike name is required")
    @EnumValidation(enumClass = MotorbikeModel.class, message = "Invalid motorbike model")
    private String motorbikeModel;

    @NotEmpty(message = "motorbike category is required")
    @EnumValidation(enumClass = MotorbikeCategory.class, message = "Invalid motorbike category")
    private String motorbikeCategory;

    @NotEmpty(message = "Motorbike fuel type is required")
    @EnumValidation(enumClass = FuelType.class, message = "Invalid fuel type")
    private String fuelType;

    @NotEmpty(message = "Motorbike engine aspiration is required")
    @EnumValidation(enumClass = EngineAspiration.class, message = "Invalid engine aspiration")
    private String engineAspiration;

    @NotEmpty(message = "Motorbike transmission type is required")
    @EnumValidation(enumClass = TransmissionType.class, message = "Invalid transmission type")
    private String transmissionType;

    // general files
    private  List<MultipartFile> generalFiles = new ArrayList<>(0);

    // exterior files
    private  List<MultipartFile> exteriorFiles = new ArrayList<>(0);

    // mechanical files
    private  List<MultipartFile> mechanicalFiles = new ArrayList<>(0);

    // document files
    private  List<MultipartFile> documentFiles = new ArrayList<>(0);
}
