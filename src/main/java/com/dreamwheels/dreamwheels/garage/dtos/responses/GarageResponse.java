package com.dreamwheels.dreamwheels.garage.dtos.responses;

import com.dreamwheels.dreamwheels.comments.dtos.CommentDto;
import com.dreamwheels.dreamwheels.garage.enums.EngineAspiration;
import com.dreamwheels.dreamwheels.garage.enums.FuelType;
import com.dreamwheels.dreamwheels.garage.enums.GarageCategory;
import com.dreamwheels.dreamwheels.garage.enums.TransmissionType;
import com.dreamwheels.dreamwheels.uploaded_files.dto.UploadedFileDto;
import com.dreamwheels.dreamwheels.users.dtos.UserResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GarageResponse {
    private String id;

    private String name;

    private String description;

    private double buyingPrice;

    private int previousOwnersCount;

    private int mileage;

    private double acceleration;

    private int topSpeed;

    private int enginePower;

    private int torque;

    private int commentsCount;

    private int likesCount;

    private TransmissionType transmissionType;

    private GarageCategory category;

    private FuelType fuelType;

    private EngineAspiration engineAspiration;

    private UserResponse user;

    private List<UploadedFileDto> garageFiles;

    private List<CommentDto> comments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
