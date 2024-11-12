package com.dreamwheels.dreamwheels.garage.controller;

import com.dreamwheels.dreamwheels.garage.models.MotorbikeGarageModel;
import com.dreamwheels.dreamwheels.garage.models.VehicleGarageModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class GarageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addVehicleGarage() throws Exception{
        VehicleGarageModel vehicleGarageDto = new VehicleGarageModel();
        vehicleGarageDto.setVehicleMake("Porsche");
        vehicleGarageDto.setVehicleModel("911 Dakar");
        vehicleGarageDto.setName("Porsche 911 Dakar");
        vehicleGarageDto.setDescription("Porsche 911 Dakar planning to go on a world tour with it");
        vehicleGarageDto.setBuyingPrice(20_0000_000.00);
        vehicleGarageDto.setPreviousOwnersCount(1);


        mockMvc.perform(
                post("/api/v1/garages/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(vehicleGarageDto))
        ).andExpectAll(
                status().isCreated(),
                content().contentType(MediaType.APPLICATION_JSON)
        ).andDo(
                print()
        );
    }

    @Test
    void addMotorbikeGarage() throws Exception {
        MotorbikeGarageModel motorbikeGarageDto = new MotorbikeGarageModel();
        motorbikeGarageDto.setName("Ducati panigale");
        motorbikeGarageDto.setDescription("My fast ducati panigae v4");
        motorbikeGarageDto.setMotorbikeMake("Ducati");
        motorbikeGarageDto.setMotorbikeModel("Panigale");
        motorbikeGarageDto.setBuyingPrice(3_200_000.00);
        motorbikeGarageDto.setPreviousOwnersCount(1);

        mockMvc.perform(
                post("/api/v1/garages/motorbike")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(motorbikeGarageDto))
        ).andExpectAll(
                status().isCreated()
        ).andDo(
                print()
        );
    }

    @Test
    void allGarages() throws Exception {
        mockMvc.perform(
                get("/api/v1/garages")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                print()
        );
    }

    @Test
    void getGarageById() throws Exception {
        mockMvc.perform(
                get("/api/v1/garages/24c94001-03ec-41bc-bd6e-3563868816e9")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(
                print()
        );
    }

    @Test
    void updateVehicleGarage() throws Exception {
        VehicleGarageModel vehicleGarageDto = new VehicleGarageModel();
        vehicleGarageDto.setVehicleMake("Porsche");
        vehicleGarageDto.setVehicleModel("911 Dakar");
        vehicleGarageDto.setName("Porsche 911 Dakar edited");
        vehicleGarageDto.setDescription("Porsche 911 Dakar planning to go on a world tour with it edited");
        vehicleGarageDto.setBuyingPrice(20_0000_000.00);
        vehicleGarageDto.setPreviousOwnersCount(1);

        mockMvc.perform(
                put("/api/v1/garages/vehicle/acebe240-2799-4f3f-bcba-9de872010c66")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(vehicleGarageDto))
        ).andExpectAll(
                status().isCreated(),
                content().contentType(MediaType.APPLICATION_JSON)
        ).andDo(
                print()
        );
    }

    @Test
    void updateMotorbikeGarage() throws Exception {
        MotorbikeGarageModel motorbikeGarageDto = new MotorbikeGarageModel();
        motorbikeGarageDto.setName("Ducati panigale edited");
        motorbikeGarageDto.setDescription("My fast ducati panigae v4");
        motorbikeGarageDto.setMotorbikeMake("Ducati");
        motorbikeGarageDto.setMotorbikeModel("Panigale");
        motorbikeGarageDto.setBuyingPrice(3_200_000.00);
        motorbikeGarageDto.setPreviousOwnersCount(1);

        mockMvc.perform(
                put("/api/v1/garages/motorbike/24c94001-03ec-41bc-bd6e-3563868816e9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(motorbikeGarageDto))
        ).andExpectAll(
                status().isCreated()
        ).andDo(
                print()
        );
    }

    @Test
    void garagesByCategory() {
    }

    @Test
    void searchGarages() {
    }

    @Test
    void deleteGarage() throws Exception {
        mockMvc.perform(
                delete("/api/v1/garages/24c94001-03ec-41bc-bd6e-3563868816e9")
        ).andExpectAll(
                status().isOk()
        ).andDo(
                print()
        );
    }
}