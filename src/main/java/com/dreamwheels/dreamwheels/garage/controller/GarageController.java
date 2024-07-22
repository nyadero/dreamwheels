package com.dreamwheels.dreamwheels.garage.controller;

import com.dreamwheels.dreamwheels.configuration.exceptions.ValidationException;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.garage.dtos.MotorbikeGarageDto;
import com.dreamwheels.dreamwheels.garage.dtos.VehicleGarageDto;
import com.dreamwheels.dreamwheels.garage.service.GarageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<GarageApiResponse> addVehicleGarage(
            @Valid @RequestBody VehicleGarageDto vehicleGarageDto,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
        return garageService.addVehicleGarage(vehicleGarageDto);
    }

    // add motorbike Garage
    @PostMapping("/motorbike")
    @PreAuthorize("isAuthenticated")
    @Operation(
            summary = "add a motorbike Garage",
            description = "Saves a motorbike Garage to the database"
    )
    public ResponseEntity<GarageApiResponse> addMotorbikeGarage(
            @Valid @RequestBody MotorbikeGarageDto motorbikeGarageDto,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
        return garageService.addMotorbikeGarage(motorbikeGarageDto);
    }

    // get all Garages
    @GetMapping("")
    @Operation(
            summary = "get all Garages",
            description = "fetches and returns a page of Garages"
    )
    @PreAuthorize("isAuthenticated")
    public ResponseEntity<GarageApiResponse> allGarages(
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
    public ResponseEntity<GarageApiResponse> getGarageById(
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
    public ResponseEntity<GarageApiResponse> updateVehicleGarage(
            @RequestBody VehicleGarageDto vehicleGarageDto,
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
        return garageService.updateVehicleGarage(vehicleGarageDto, id);
    }

    // update motorbike Garage
    @PutMapping("/motorbike/{id}")
    @PreAuthorize("isAuthenticated")
    @Operation(
            summary = "update motorbike Garage",
            description = "Updates a motorbike Garage"
    )
    public ResponseEntity<GarageApiResponse> updateMotorbikeGarage(
            @RequestBody MotorbikeGarageDto motorbikeGarageDto,
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
        return garageService.updateMotorbikeGarage(motorbikeGarageDto, id);
    }

    // get Garages by category
    @GetMapping("/category/{category}")
    @Operation(
            summary = "Get Garages by category",
            description = "Fetches and returns Garages by their category ie motorbike, vehicle etc"
    )
    public ResponseEntity<GarageApiResponse> GaragesByCategory(
            @PathVariable("category") String category
    ){
        return garageService.garagesByCategory(category);
    }

    // search vehicle Garages
    @GetMapping("/vehicles/search")
    @Operation(
            summary = "Search vehicle garages",
            description = "Fetches and returns vehicle garages matching a given criteria"
    )
    public ResponseEntity<GarageApiResponse> searchGarages(
            @RequestParam(name = "query") String query
    ){
        return garageService.searchGarages(query);
    }


    // search motorbike garages
    @GetMapping("/motorbikes/search")
    @Operation(
            summary = "Search motorbike garages",
            description = "Fetches and returns motorbike garages matching a given criteria"
    )
    public ResponseEntity<GarageApiResponse> searchMotorbikeGarages(

    ){
        return garageService.searchMotorbikeGarages();
    }


    // delete Garage
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated")
    @Operation(
            summary = "delete Garage",
            description = "Deletes a Garage from the database"
    )
    public ResponseEntity<GarageApiResponse> deleteGarage(
            @PathVariable("id") String id
    ){
        return garageService.deleteGarage(id);
    }

}
