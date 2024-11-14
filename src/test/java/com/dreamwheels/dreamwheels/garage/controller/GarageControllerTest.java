package com.dreamwheels.dreamwheels.garage.controller;

import com.dreamwheels.dreamwheels.configuration.security.jwt.JwtUtils;
import com.dreamwheels.dreamwheels.garage.models.MotorbikeGarageModel;
import com.dreamwheels.dreamwheels.garage.models.VehicleGarageModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

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

    @Autowired
    private JwtUtils jwtUtils;

    private String jwtToken;

    @BeforeAll
    static void setup() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }

    @BeforeEach
    void generateJwtToken() {
        // Create a test user to generate the JWT
        // Make sure the user is enabled and also registered
        UserDetails testUser = new User("briannyadero@gmail.com", "password", Collections.emptyList());
        jwtToken = jwtUtils.generateJwtToken(testUser);
    }

    @AfterEach
    void tearDown() {
        jwtToken = null;
    }

    @Test
    void addVehicleGarage() throws Exception{
        MultiValueMap<String, String> vehicleGarageModel = new LinkedMultiValueMap<>();
        vehicleGarageModel.add("vehicleMake", "Porsche");
        vehicleGarageModel.add("vehicleModel","Dakar");
        vehicleGarageModel.add("name","Porsche 911 Dakar 2024");
        vehicleGarageModel.add("description", "Porsche 911 Dakar planning to go on a world tour with it");
        vehicleGarageModel.add("buyingPrice", String.valueOf(20_0000_000.00));
        vehicleGarageModel.add("previousOwnersCount", String.valueOf(1));
        vehicleGarageModel.add("acceleration", String.valueOf(3));
        vehicleGarageModel.add("bodyType", "Coupe");
        vehicleGarageModel.add("driveTrain", "Rwd");
        vehicleGarageModel.add("fuelType", "Petrol");
        vehicleGarageModel.add("engineAspiration","Turbocharged");
        vehicleGarageModel.add("engineLayout","V6");
        vehicleGarageModel.add("enginePosition","Rear");
        vehicleGarageModel.add("enginePower", String.valueOf(600));
        vehicleGarageModel.add("mileage", String.valueOf(10000));
        vehicleGarageModel.add("torque", String.valueOf(589));
        vehicleGarageModel.add("topSpeed", String.valueOf(340));
        vehicleGarageModel.add("transmissionType","Manual");

        mockMvc.perform(
                post("/api/v1/garages/vehicle")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .params(vehicleGarageModel)
        ).andExpectAll(
                status().isCreated(),
                content().contentType(MediaType.APPLICATION_JSON)
        ).andDo(
                print()
        );
    }


    @Test
    void addMotorbikeGarage() throws Exception {

    }

    @Test
    void allGarages() throws Exception {

    }

    @Test
    void getGarageById() throws Exception {

    }

    @Test
    void updateVehicleGarage() throws Exception {

    }

    @Test
    void updateMotorbikeGarage() throws Exception {

    }

    @Test
    void garagesByCategory() {
    }

    @Test
    void searchGarages() {
    }

    @Test
    void deleteGarage() throws Exception {

    }
}