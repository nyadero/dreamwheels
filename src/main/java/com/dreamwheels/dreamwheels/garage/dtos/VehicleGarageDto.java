package com.dreamwheels.dreamwheels.garage.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class VehicleGarageDto {
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

    @NotEmpty(message = "Vehicle make is required")
    private String vehicleMake;

    @NotEmpty(message = "Vehicle name is required")
    private String vehicleModel;

    @NotEmpty(message = "Vehicle name is required")
    private String vehicleFuelType;

    @NotEmpty(message = "Vehicle name is required")
    private String vehicleTransmission;

    @NotNull(message = "Vehicle mileage is required")
    @Range(min = 0, max = 1000000, message = "Vehicle mileage must be between 0 and 1,000,000")
    private Integer vehicleMileage;
}
