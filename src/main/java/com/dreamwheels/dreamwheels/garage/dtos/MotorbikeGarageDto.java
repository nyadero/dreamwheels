package com.dreamwheels.dreamwheels.garage.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class MotorbikeGarageDto {
    @NotEmpty(message = "motorbike name is required")
    private String name;

    @NotEmpty(message = "motorbike description is required")
    private String description;

    //    @Digits(
//            message = "buying price should be a number", integer = 0, fraction = 0
//    )
    private Double buyingPrice;

    //    @Digits(
//            message = "previous owners should be a number", integer = 0, fraction = 0
//    )
    private Integer previousOwnersCount;

    @NotEmpty(message = "motorbike make is required")
    private String motorbikeMake;

    @NotEmpty(message = "motorbike name is required")
    private String motorbikeModel;

    @NotEmpty(message = "motorbike name is required")
    private String motorbikeFuelType;

    @NotEmpty(message = "motorbike name is required")
    private String motorbikeTransmission;

    //    @Digits(
//            message = "mileage should be a number", integer = 0, fraction = 0
//    )
    @Range()
    private Integer motorbikeMileage;
}
