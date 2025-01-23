package com.dreamwheels.dreamwheels.garage.controller;

import com.dreamwheels.dreamwheels.configuration.exceptions.ValidationException;
import com.dreamwheels.dreamwheels.configuration.responses.CustomPageResponse;
import com.dreamwheels.dreamwheels.configuration.responses.Data;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import com.dreamwheels.dreamwheels.garage.dtos.GarageResponse;
import com.dreamwheels.dreamwheels.garage.dtos.requests.MotorbikeGarageRequest;
import com.dreamwheels.dreamwheels.garage.dtos.requests.VehicleGarageRequest;
import com.dreamwheels.dreamwheels.garage.service.GarageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/garages")
public class GarageController {

    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    // add a vehicle Garage
    @PostMapping("/vehicle")
    @PreAuthorize("isAuthenticated")
    @Operation(
            summary = "add a vehicle Garage",
            description = "Saves a vehicle Garage to the database"
    )
    public ResponseEntity<GarageApiResponse<GarageResponse>> addVehicleGarage(
            HttpServletRequest httpRequest,
            @Valid @ModelAttribute VehicleGarageRequest vehicleGarageDto,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
        GarageResponse garage = garageService.addVehicleGarage(vehicleGarageDto, httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new GarageApiResponse<>(new Data<>(garage), "Garage vehicle added", ResponseType.SUCCESS)
        );
    }

    // add motorbike Garage
    @PostMapping("/motorbike")
    @PreAuthorize("isAuthenticated")
    @Operation(
            summary = "add a motorbike Garage",
            description = "Saves a motorbike Garage to the database"
    )
    public ResponseEntity<GarageApiResponse<GarageResponse>> addMotorbikeGarage(
            HttpServletRequest httpServletRequest,
            @Valid @ModelAttribute MotorbikeGarageRequest motorbikeGarageDto,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
        GarageResponse garage = garageService.addMotorbikeGarage(motorbikeGarageDto, httpServletRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new GarageApiResponse<>(new Data<>(garage), "Garage vehicle added", ResponseType.SUCCESS)
        );
    }

    // get all Garages
    @GetMapping
    @Operation(
            summary = "get all Garages",
            description = "fetches and returns a page of Garages"
    )
    public ResponseEntity<GarageApiResponse<CustomPageResponse<GarageResponse>>> allGarages(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber
    ){
        CustomPageResponse<GarageResponse> garages = garageService.allGarages(pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(new Data<>(garages), "Garages fetched", ResponseType.SUCCESS)
        );
    }
    
    // get Garage by id'
    @GetMapping("/{id}")
    @Operation(
            summary = "Get Garage by id",
            description = "Fetches and returns a Garage by its id"
    )
    public ResponseEntity<GarageApiResponse<GarageResponse>> getGarageById(
           @PathVariable("id") String id
    ){
        GarageResponse garage = garageService.getGarageById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new GarageApiResponse<>(new Data<>(garage), "Found garage", ResponseType.SUCCESS));
    }

    // update vehicle Garage
    @PutMapping("/vehicle/{id}")
    @PreAuthorize("isAuthenticated")
    @Operation(
            summary = "update vehicle",
            description = "Updates a motorbike Garage"
    )
    public ResponseEntity<GarageApiResponse<GarageResponse>> updateVehicleGarage(
            HttpServletRequest httpRequest,
            @Valid @ModelAttribute VehicleGarageRequest vehicleGarageDto,
            @PathVariable("id") String id,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
        GarageResponse garage = garageService.updateVehicleGarage(vehicleGarageDto, id, httpRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(new Data<>(garage), "Garage updated", ResponseType.SUCCESS)
        );
    }

    // update motorbike Garage
    @PutMapping("/motorbike/{id}")
    @PreAuthorize("isAuthenticated")
    @Operation(
            summary = "update motorbike Garage",
            description = "Updates a motorbike Garage"
    )
    public ResponseEntity<GarageApiResponse<GarageResponse>> updateMotorbikeGarage(
            HttpServletRequest httpServletRequest,
            @Valid @ModelAttribute MotorbikeGarageRequest motorbikeGarageDto,
            @PathVariable("id") String id,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
        GarageResponse garage =  garageService.updateMotorbikeGarage(motorbikeGarageDto, id, httpServletRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(new Data<>(garage), "Motorbike updated", ResponseType.SUCCESS)
        );
    }

    // get Garages by category
    @GetMapping("/category/{category}")
    @Operation(
            summary = "Get Garages by category",
            description = "Fetches and returns Garages by their category ie motorbike, vehicle etc"
    )
    public ResponseEntity<GarageApiResponse<CustomPageResponse<GarageResponse>>> GaragesByCategory(
            @PathVariable("category") String category,
            @RequestParam(name="pageNumber", defaultValue = "0") Integer pageNumber
    ){
        CustomPageResponse<GarageResponse> garages = garageService.garagesByCategory(category, pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(new Data<>(garages), "Garage updated", ResponseType.SUCCESS)
        );
    }

    // search vehicle Garages
    @GetMapping("/vehicles/search")
    @Operation(
            summary = "Search vehicle garages",
            description = "Fetches and returns vehicle garages matching a given criteria"
    )
    public ResponseEntity<GarageApiResponse<CustomPageResponse<GarageResponse>>> searchGarages(
            @RequestParam(name="pageNumber", defaultValue = "0", required = true) Integer pageNumber,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "vehicleMake", required = false) String vehicleMake,
            @RequestParam(name = "vehicleModel", required = false) String vehicleModel,
            @RequestParam(name = "mileage", required = false) Integer mileage,
            @RequestParam(name="previousOwnersCount", required = false) Integer previousOwnersCount,
            @RequestParam(name="enginePower", required = false) Integer enginePower,
            @RequestParam(name="topSpeed", required = false) Integer topSpeed,
            @RequestParam(name="acceleration", required = false) Integer acceleration,
            @RequestParam(name = "transmissionType", required = false) String transmissionType,
            @RequestParam(name = "driveTrain", required = false) String driveTrain,
            @RequestParam(name = "enginePosition", required = false) String enginePosition,
            @RequestParam(name = "engineLayout", required = false) String engineLayout,
            @RequestParam(name = "engineAspiration", required = false) String engineAspiration,
            @RequestParam(name = "bodyType", required = false) String bodyType
    ){
        CustomPageResponse<GarageResponse> garages = garageService.searchGarages(
                pageNumber, name, vehicleMake, vehicleModel, mileage, previousOwnersCount, enginePower, topSpeed, acceleration,
                transmissionType, driveTrain, enginePosition, engineLayout, engineAspiration, bodyType
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(new Data<>(garages), "Vehicle garages searched", ResponseType.SUCCESS)
        );
    }


    // search motorbike garages
    @GetMapping("/motorbikes/search")
    @Operation(
            summary = "Search motorbike garages",
            description = "Fetches and returns motorbike garages matching a given criteria"
    )
    public ResponseEntity<GarageApiResponse<CustomPageResponse<GarageResponse>>> searchMotorbikeGarages(
            @RequestParam(name="pageNumber", defaultValue = "0", required = true) Integer pageNumber,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "motorbikeMake", required = false) String motorbikeMake,
            @RequestParam(name = "motorbikeModel", required = false) String motorbikeModel,
            @RequestParam(name = "motorbikeCategory", required = false) String motorbikeCategory,
            @RequestParam(name = "mileage", required = false) Integer mileage,
            @RequestParam(name="previousOwnersCount", required = false) Integer previousOwnersCount,
            @RequestParam(name="enginePower", required = false) Integer enginePower,
            @RequestParam(name="topSpeed", required = false) Integer topSpeed,
            @RequestParam(name="acceleration", required = false) Integer acceleration,
            @RequestParam(name = "transmissionType", required = false) String transmissionType,
            @RequestParam(name = "engineLayout", required = false) String engineLayout,
            @RequestParam(name = "engineAspiration", required = false) String engineAspiration
    ){

        CustomPageResponse<GarageResponse> garages = garageService.searchMotorbikeGarages(
                pageNumber, name, motorbikeCategory, motorbikeModel, motorbikeCategory, mileage, previousOwnersCount,
                enginePower, topSpeed, acceleration, transmissionType, engineAspiration, engineLayout
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(new Data<>(garages), "Motorbike garages searched", ResponseType.SUCCESS)
        );
    }

    // delete Garage
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated")
    @Operation(
            summary = "delete Garage",
            description = "Deletes a Garage from the database"
    )
    public ResponseEntity<GarageApiResponse<Void>> deleteGarage(
            @PathVariable("id") String id
    ){
        garageService.deleteGarage(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(null, "Garage deleted", ResponseType.SUCCESS)
        );
    }


}
