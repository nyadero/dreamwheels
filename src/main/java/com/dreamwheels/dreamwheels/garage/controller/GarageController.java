package com.dreamwheels.dreamwheels.garage.controller;

import com.dreamwheels.dreamwheels.configuration.exceptions.ValidationException;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.garage.dtos.MotorbikeGarageDto;
import com.dreamwheels.dreamwheels.garage.dtos.VehicleGarageDto;
import com.dreamwheels.dreamwheels.garage.entity.Garage;
import com.dreamwheels.dreamwheels.garage.service.GarageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Autowired
    private GarageService garageService;

    // add a vehicle Garage
    @PostMapping("/vehicle")
    @PreAuthorize("isAuthenticated")
    @Operation(
            summary = "add a vehicle Garage",
            description = "Saves a vehicle Garage to the database"
    )
    public ResponseEntity<GarageApiResponse<Garage>> addVehicleGarage(
            HttpServletRequest httpRequest,
            @Valid @ModelAttribute VehicleGarageDto vehicleGarageDto,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
        return garageService.addVehicleGarage(vehicleGarageDto, httpRequest);
    }

    // add motorbike Garage
    @PostMapping("/motorbike")
    @PreAuthorize("isAuthenticated")
    @Operation(
            summary = "add a motorbike Garage",
            description = "Saves a motorbike Garage to the database"
    )
    public ResponseEntity<GarageApiResponse<Garage>> addMotorbikeGarage(
            HttpServletRequest httpServletRequest,
            @Valid @ModelAttribute MotorbikeGarageDto motorbikeGarageDto,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
        return garageService.addMotorbikeGarage(motorbikeGarageDto, httpServletRequest);
    }

    // get all Garages
    @GetMapping("")
    @Operation(
            summary = "get all Garages",
            description = "fetches and returns a page of Garages"
    )
    @PreAuthorize("isAuthenticated")
    public ResponseEntity<GarageApiResponse<Page<Garage>>> allGarages(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber
    ){
        return garageService.allGarages(pageNumber);
    }

    // get Garage by id'
    @GetMapping("/{id}")
    @Operation(
            summary = "Get Garage by id",
            description = "Fetches and returns a Garage by its id"
    )
    public ResponseEntity<GarageApiResponse<Garage>> getGarageById(
           @PathVariable("id") String id
    ){
        return garageService.getGarageById(id);
    }

    // update vehicle Garage
    @PutMapping("/vehicle/{id}")
    @PreAuthorize("isAuthenticated")
    @Operation(
            summary = "Get Garage by id",
            description = "Fetches and returns a Garage by its id"
    )
    public ResponseEntity<GarageApiResponse<Garage>> updateVehicleGarage(
            HttpServletRequest httpRequest,
            @Valid @ModelAttribute VehicleGarageDto vehicleGarageDto,
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
        return garageService.updateVehicleGarage(vehicleGarageDto, id, httpRequest);
    }

    // update motorbike Garage
    @PutMapping("/motorbike/{id}")
    @PreAuthorize("isAuthenticated")
    @Operation(
            summary = "update motorbike Garage",
            description = "Updates a motorbike Garage"
    )
    public ResponseEntity<GarageApiResponse<Garage>> updateMotorbikeGarage(
            HttpServletRequest httpServletRequest,
            @Valid @ModelAttribute MotorbikeGarageDto motorbikeGarageDto,
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
        return garageService.updateMotorbikeGarage(motorbikeGarageDto, id, httpServletRequest);
    }

    // get Garages by category
    @GetMapping("/category/{category}")
    @Operation(
            summary = "Get Garages by category",
            description = "Fetches and returns Garages by their category ie motorbike, vehicle etc"
    )
    public ResponseEntity<GarageApiResponse<Page<Garage>>> GaragesByCategory(
            @PathVariable("category") String category,
            @RequestParam(name="pageNumber", defaultValue = "0") Integer pageNumber
    ){
        return garageService.garagesByCategory(category, pageNumber);
    }

    // search vehicle Garages
    @GetMapping("/vehicles/search")
    @Operation(
            summary = "Search vehicle garages",
            description = "Fetches and returns vehicle garages matching a given criteria"
    )
    public ResponseEntity<GarageApiResponse<Page<Garage>>> searchGarages(
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
        return garageService.searchGarages(
                pageNumber, name, vehicleMake, vehicleModel, mileage, previousOwnersCount, enginePower, topSpeed, acceleration,
                transmissionType, driveTrain, enginePosition, engineLayout, engineAspiration, bodyType
        );
    }


    // search motorbike garages
    @GetMapping("/motorbikes/search")
    @Operation(
            summary = "Search motorbike garages",
            description = "Fetches and returns motorbike garages matching a given criteria"
    )
    public ResponseEntity<GarageApiResponse<Page<Garage>>> searchMotorbikeGarages(
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
        return garageService.searchMotorbikeGarages(
                pageNumber, name, motorbikeCategory, motorbikeModel, motorbikeCategory, mileage, previousOwnersCount,
                enginePower, topSpeed, acceleration, transmissionType, engineAspiration, engineLayout
        );
    }

    // delete Garage
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated")
    @Operation(
            summary = "delete Garage",
            description = "Deletes a Garage from the database"
    )
    public ResponseEntity<GarageApiResponse<Garage>> deleteGarage(
            @PathVariable("id") String id
    ){
        return garageService.deleteGarage(id);
    }

}
