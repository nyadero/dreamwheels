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
import com.dreamwheels.dreamwheels.uploaded_files.enums.FileTags;
import com.dreamwheels.dreamwheels.uploaded_files.event.UploadedFileEvent;
import com.dreamwheels.dreamwheels.uploaded_files.event.UploadedFileEventType;
import com.dreamwheels.dreamwheels.uploaded_files.eventlistener.UploadedFileEventListener;
import com.dreamwheels.dreamwheels.users.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarageServiceImpl implements GarageService {
    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private UploadedFileEventListener uploadedFileEventListener;

    public static final int PAGE_SIZE = 20;

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> addVehicleGarage(VehicleGarageDto vehicleGarageDto, HttpServletRequest httpRequest) {
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
        garage.setCategory(GarageCategory.Vehicle);
        garage.setTransmissionType(TransmissionType.valueOf(vehicleGarageDto.getTransmissionType()));
        garage.setUser(authenticatedUser());

        Garage saved = garageRepository.save(garage);

        uploadVehicleFiles(vehicleGarageDto, saved, httpRequest);
        saved.setGarageFiles(uploadedFileEventListener.getUploadedFiles());

        return new ResponseEntity<>(new GarageApiResponse(saved, "Your vehicle garage has been saved", ResponseType.SUCCESS),
                HttpStatus.CREATED);
    }

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> addMotorbikeGarage(
            MotorbikeGarageDto motorbikeGarageDto,
            HttpServletRequest httpRequest
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
        garage.setCategory(GarageCategory.Motorbike);
        garage.setTransmissionType(TransmissionType.valueOf(motorbikeGarageDto.getTransmissionType()));
        garage.setUser(authenticatedUser());

        Garage saved = garageRepository.save(garage);

        uploadMotorbikeFiles(motorbikeGarageDto, httpRequest, saved);
        saved.setGarageFiles(uploadedFileEventListener.getUploadedFiles());

        return new ResponseEntity<>(new GarageApiResponse(saved, "Your motorbike garage has been saved", ResponseType.SUCCESS),
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
    public ResponseEntity<GarageApiResponse> updateVehicleGarage(VehicleGarageDto vehicleGarageDto, String id, HttpServletRequest httpRequest) {
        Garage garage = garageRepository.findByIdAndUserId(id, authenticatedUser().getId()).orElseThrow(() -> new EntityNotFoundException("Garage not found"));
        if (garage instanceof Vehicle vehicle){
            vehicle.setName(vehicleGarageDto.getName());
            vehicle.setDescription(vehicleGarageDto.getDescription());
            vehicle.setBuyingPrice(vehicleGarageDto.getBuyingPrice());
            vehicle.setPreviousOwnersCount(vehicleGarageDto.getPreviousOwnersCount());
            vehicle.setAcceleration(vehicleGarageDto.getAcceleration());
            vehicle.setMileage(vehicleGarageDto.getMileage());
            vehicle.setEnginePower(vehicleGarageDto.getEnginePower());
            vehicle.setEngineAspiration(EngineAspiration.valueOf(vehicleGarageDto.getEngineAspiration()));
            vehicle.setFuelType(FuelType.valueOf(vehicleGarageDto.getFuelType()));
            vehicle.setTorque(vehicleGarageDto.getTorque());
            vehicle.setTopSpeed(vehicleGarageDto.getTopSpeed());
            vehicle.setCategory(GarageCategory.Vehicle);
            vehicle.setTransmissionType(TransmissionType.valueOf(vehicleGarageDto.getTransmissionType()));
            vehicle.setVehicleMake(VehicleMake.fromString(vehicleGarageDto.getVehicleMake()).getDisplayName());
            vehicle.setVehicleModel(VehicleModel.valueOf(vehicleGarageDto.getVehicleModel()));
            vehicle.setDriveTrain(DriveTrain.valueOf(vehicleGarageDto.getDriveTrain()));
            vehicle.setBodyType(BodyType.valueOf(vehicleGarageDto.getBodyType()));
            vehicle.setEngineLayout(EngineLayout.valueOf(vehicleGarageDto.getEngineLayout()));
            vehicle.setEnginePosition(EnginePosition.valueOf(vehicleGarageDto.getEnginePosition()));

            Garage saved = garageRepository.save(vehicle);

            uploadVehicleFiles(vehicleGarageDto, saved, httpRequest);

        }

        return new ResponseEntity<>(new GarageApiResponse(null, "Vehicle garage updated successfully", ResponseType.SUCCESS), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GarageApiResponse> updateMotorbikeGarage(MotorbikeGarageDto motorbikeGarageDto, String id, HttpServletRequest httpServletRequest) {
        Garage garage = garageRepository.findByIdAndUserId(id, authenticatedUser().getId()).orElseThrow(() -> new EntityNotFoundException("Garage not found"));
        if (garage instanceof Motorbike motorbike){
            motorbike.setName(motorbikeGarageDto.getName());
            motorbike.setDescription(motorbikeGarageDto.getDescription());
            motorbike.setBuyingPrice(motorbikeGarageDto.getBuyingPrice());
            motorbike.setPreviousOwnersCount(motorbikeGarageDto.getPreviousOwnersCount());
            motorbike.setAcceleration(motorbikeGarageDto.getAcceleration());
            motorbike.setMileage(motorbikeGarageDto.getMileage());
            motorbike.setEnginePower(motorbikeGarageDto.getEnginePower());
            motorbike.setEngineAspiration(EngineAspiration.valueOf(motorbikeGarageDto.getEngineAspiration()));
            motorbike.setFuelType(FuelType.valueOf(motorbikeGarageDto.getFuelType()));
            motorbike.setTorque(motorbikeGarageDto.getTorque());
            motorbike.setTopSpeed(motorbikeGarageDto.getTopSpeed());
            motorbike.setCategory(GarageCategory.Motorbike);
            motorbike.setTransmissionType(TransmissionType.valueOf(motorbikeGarageDto.getTransmissionType()));
            motorbike.setMotorbikeMake(MotorbikeMake.valueOf(motorbikeGarageDto.getMotorbikeMake()));
            motorbike.setMotorbikeModel(MotorbikeModel.valueOf(motorbikeGarageDto.getMotorbikeModel()));
            motorbike.setMotorbikeCategory(MotorbikeCategory.valueOf(motorbikeGarageDto.getMotorbikeCategory()));

            Garage saved = garageRepository.save(motorbike);
            uploadMotorbikeFiles(motorbikeGarageDto, httpServletRequest, saved);
        }
        return new ResponseEntity<>(new GarageApiResponse(null, "Motorbike garage updated successfully",ResponseType.SUCCESS), HttpStatus.CREATED);
    }

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> garagesByCategory(String category, Integer pageNumber) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE, sort);
        Page<Garage> garagePage = garageRepository.findAllByCategory(GarageCategory.valueOf(category), pageRequest);
        return new ResponseEntity<>(new GarageApiResponse(
                garagePage,
                "Found " + garagePage.getTotalElements(),
                ResponseType.SUCCESS
        ), HttpStatus.OK);
    }

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> searchGarages(
            Integer pageNumber, String name, String vehicleMake, String vehicleModel, Integer mileage, Integer previousOwnersCount, Integer enginePower,
            Integer topSpeed, Integer acceleration, String transmissionType, String driveTrain, String enginePosition, String engineLayout, String engineAspiration, String bodyType
    ) {
        Specification<Garage> vehiclesSpecification = Specification.where(null);
        // if name is not null
        if (name != null && !name.isEmpty()){
            System.out.println("name " + name);
            vehiclesSpecification = vehiclesSpecification.and((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), name)
            );
        }

        // if vehicle make is not null
        if (vehicleMake != null && !vehicleMake.isEmpty()){
            vehiclesSpecification = vehiclesSpecification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("vehicleMake"), VehicleMake.fromString(vehicleMake))
            );
        }

        // if vehicle model is not null
        if (vehicleModel != null && !vehicleModel.isEmpty()){
            vehiclesSpecification = vehiclesSpecification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("vehicleModel"), vehicleModel)
            );
        }

        // if vehicle drivetrain is not null
        if (driveTrain != null && !driveTrain.isEmpty()){
            vehiclesSpecification = vehiclesSpecification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("driveTrain"), DriveTrain.valueOf(driveTrain))
            );
        }

        //  vehicle engine aspiration
        if (engineAspiration != null && !engineAspiration.isEmpty()){
            vehiclesSpecification = vehiclesSpecification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("engineAspiration"), EngineAspiration.valueOf(engineAspiration))
            );
        }

        // vehicle engine position
        if (enginePosition != null && !enginePosition.isEmpty()){
            vehiclesSpecification = vehiclesSpecification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("enginePosition"), EnginePosition.valueOf(enginePosition))
            );
        }

        // body type
        if (bodyType != null && !bodyType.isEmpty()){
            vehiclesSpecification = vehiclesSpecification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("bodyType"), BodyType.valueOf(bodyType))
            );
        }

        // transmission type
        if (transmissionType != null && !transmissionType.isEmpty()){
            vehiclesSpecification = vehiclesSpecification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("transmissionType"), TransmissionType.valueOf(transmissionType))
            );
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE, sort);
        Page<Garage> garagePage = garageRepository.findAll(vehiclesSpecification, pageRequest);
        return new ResponseEntity<>(new GarageApiResponse(
                garagePage,
                "Query returned " + garagePage.getTotalElements(),
                ResponseType.SUCCESS
        ), HttpStatus.OK);
    }

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> searchMotorbikeGarages(
            Integer pageNumber, String name, String motorbikeMake, String motorbikeModel, String motorbikeCategory, Integer mileage,
            Integer previousOwnersCount, Integer enginePower, Integer topSpeed, Integer acceleration, String transmissionType,
            String engineAspiration, String engineLayout
    ) {
        Specification<Garage> garageSpecification = Specification.where(null);

        // if make is not null
        if (motorbikeMake != null && !motorbikeMake.isEmpty()){
             garageSpecification = garageSpecification.and((root, query, criteriaBuilder) ->
                             criteriaBuilder.equal(root.get("motorbikeMake"), MotorbikeMake.valueOf(motorbikeMake))
             );
        }

        // if motorbike model is not null
        if (motorbikeModel != null && !motorbikeModel.isEmpty()){
            garageSpecification = garageSpecification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("motorbikeModel"), MotorbikeModel.valueOf(motorbikeModel))
            );
        }

        // motorbike category
        if (motorbikeCategory != null && !motorbikeCategory.isEmpty()){
            garageSpecification = garageSpecification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("motorbikeCategory"), MotorbikeCategory.valueOf(motorbikeCategory))
            );
        }

        // transmission type
        if (transmissionType != null && !transmissionType.isEmpty()){
            garageSpecification = garageSpecification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("transmissionType"), TransmissionType.valueOf(transmissionType))
            );
        }

        // engine aspiration
        if (engineAspiration != null && !engineAspiration.isEmpty()){
            garageSpecification = garageSpecification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("engineAspiration"), EngineAspiration.valueOf(engineAspiration))
            );
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE, sort);
        Page<Garage> garagePage = garageRepository.findAll(garageSpecification, pageRequest);
        return new ResponseEntity<>(new GarageApiResponse(
                garagePage,
                "Query returned " + garagePage.getTotalElements(),
                ResponseType.SUCCESS
        ), HttpStatus.OK);
    }

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> deleteGarage(String id) {
        Garage garage = garageRepository.findByIdAndUserId(id, authenticatedUser().getId()).orElseThrow(() -> new EntityNotFoundException("Garage not found"));
        System.out.println(garage.getName());
        applicationEventPublisher.publishEvent(new UploadedFileEvent(garage, List.of(), UploadedFileEventType.Delete, authenticatedUser(), null, null, garage.getGarageFiles()));
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

    private void uploadVehicleFiles(VehicleGarageDto vehicleGarageDto, Garage saved, HttpServletRequest httpRequest) {
        // publish event to upload general files
        if (!vehicleGarageDto.getGeneralFiles().isEmpty()){
            applicationEventPublisher.publishEvent(new UploadedFileEvent(saved, vehicleGarageDto.getGeneralFiles(), UploadedFileEventType.Upload, authenticatedUser(), FileTags.General, applicationUrl(httpRequest), null));
        }

        // publish event to upload interior files
        if (!vehicleGarageDto.getInteriorFiles().isEmpty()){
            applicationEventPublisher.publishEvent(new UploadedFileEvent(saved, vehicleGarageDto.getGeneralFiles(),  UploadedFileEventType.Upload, authenticatedUser(), FileTags.Interior, applicationUrl(httpRequest), null));
        }

        // publish event to upload exterior files
        if (!vehicleGarageDto.getExteriorFiles().isEmpty()){
            applicationEventPublisher.publishEvent(new UploadedFileEvent(saved, vehicleGarageDto.getExteriorFiles(),  UploadedFileEventType.Upload, authenticatedUser(), FileTags.Exterior, applicationUrl(httpRequest), null));
        }

        // publish event to upload mechanical files
        if (!vehicleGarageDto.getMechanicalFiles().isEmpty()){
            applicationEventPublisher.publishEvent(new UploadedFileEvent(saved, vehicleGarageDto.getMechanicalFiles(), UploadedFileEventType.Upload, authenticatedUser(), FileTags.Mechanical, applicationUrl(httpRequest), null));
        }

        // publish event to upload document files
        if (!vehicleGarageDto.getDocumentFiles().isEmpty()){
            applicationEventPublisher.publishEvent(new UploadedFileEvent(saved, vehicleGarageDto.getDocumentFiles(),  UploadedFileEventType.Upload, authenticatedUser(), FileTags.Documents, applicationUrl(httpRequest), null));
        }
    }


    private void uploadMotorbikeFiles(MotorbikeGarageDto motorbikeGarageDto, HttpServletRequest httpRequest, Garage garage) {
        // upload general files
        if (!motorbikeGarageDto.getGeneralFiles().isEmpty()){
            applicationEventPublisher.publishEvent(new UploadedFileEvent(garage, motorbikeGarageDto.getGeneralFiles(), UploadedFileEventType.Upload, authenticatedUser(), FileTags.General, applicationUrl(httpRequest), null));
        }

        // upload exterior files
        if (!motorbikeGarageDto.getExteriorFiles().isEmpty()){
            applicationEventPublisher.publishEvent(new UploadedFileEvent(garage, motorbikeGarageDto.getExteriorFiles(), UploadedFileEventType.Upload, authenticatedUser(), FileTags.Exterior, applicationUrl(httpRequest), null));
        }

        // upload mechanical files
        if (!motorbikeGarageDto.getMechanicalFiles().isEmpty()){
            applicationEventPublisher.publishEvent(new UploadedFileEvent(garage, motorbikeGarageDto.getMechanicalFiles(), UploadedFileEventType.Upload, authenticatedUser(), FileTags.Mechanical, applicationUrl(httpRequest), null));
        }

        // upload document files
        if (!motorbikeGarageDto.getDocumentFiles().isEmpty()){
            applicationEventPublisher.publishEvent(new UploadedFileEvent(garage, motorbikeGarageDto.getDocumentFiles(), UploadedFileEventType.Upload, authenticatedUser(), FileTags.Documents, applicationUrl(httpRequest), null));
        }
    }

    private static String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }


}
