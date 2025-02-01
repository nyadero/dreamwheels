package com.dreamwheels.dreamwheels.garage.controller;

import com.dreamwheels.dreamwheels.garage.dtos.responses.GarageResponse;
import com.dreamwheels.dreamwheels.garage.serviceimpl.GarageServiceImpl;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GarageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GarageServiceImpl garageService;

    @BeforeAll
    static void setup() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }

    @Test
    void addVehicleGarage() throws Exception {
        MultiValueMap<String, String> vehicleGarageModel = new LinkedMultiValueMap<>();
        vehicleGarageModel.add("vehicleMake", "Porsche");
        vehicleGarageModel.add("vehicleModel", "Dakar");
        vehicleGarageModel.add("name", "Porsche 911 Dakar 2024");
        vehicleGarageModel.add("description", "Porsche 911 Dakar planning to go on a world tour with it");
        vehicleGarageModel.add("buyingPrice", String.valueOf(20_0000_000.00));
        vehicleGarageModel.add("previousOwnersCount", String.valueOf(1));
        vehicleGarageModel.add("acceleration", String.valueOf(3));
        vehicleGarageModel.add("bodyType", "Coupe");
        vehicleGarageModel.add("driveTrain", "Rwd");
        vehicleGarageModel.add("fuelType", "Petrol");
        vehicleGarageModel.add("engineAspiration", "Turbocharged");
        vehicleGarageModel.add("engineLayout", "V6");
        vehicleGarageModel.add("enginePosition", "Rear");
        vehicleGarageModel.add("enginePower", String.valueOf(600));
        vehicleGarageModel.add("mileage", String.valueOf(10000));
        vehicleGarageModel.add("torque", String.valueOf(589));
        vehicleGarageModel.add("topSpeed", String.valueOf(340));
        vehicleGarageModel.add("transmissionType", "Manual");
        when(garageService.addVehicleGarage(any(), any())).thenReturn(new GarageResponse());

        var response = mockMvc.perform(
                        post("/api/v1/garages/vehicle")
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .params(vehicleGarageModel))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        print())
                .andReturn();

        assertThat(response).isNotNull();
        assertThat(response.getResponse().getContentAsString()).contains("Garage vehicle added");
        verify(garageService).addVehicleGarage(any(), any());
    }

    @Test
    void addMotorbikeGarage() throws Exception {
        var motorBikeGarageModel = new LinkedMultiValueMap<String, String>();

        motorBikeGarageModel.add("name", "Ducati");
        motorBikeGarageModel.add("description", "Duper fast and racy");
        motorBikeGarageModel.add("buyingPrice", String.valueOf(12000));
        motorBikeGarageModel.add("previousOwnersCount", String.valueOf(1));
        motorBikeGarageModel.add("acceleration", String.valueOf(3));
        motorBikeGarageModel.add("mileage", String.valueOf(2000));
        motorBikeGarageModel.add("topSpeed", String.valueOf(300));
        motorBikeGarageModel.add("enginePower", String.valueOf(120));
        motorBikeGarageModel.add("torque", String.valueOf(90));
        motorBikeGarageModel.add("motorbikeMake", "Ducati");
        motorBikeGarageModel.add("motorbikeModel", "Ninja");
        motorBikeGarageModel.add("motorbikeCategory", "Sports");
        motorBikeGarageModel.add("fuelType", "Petrol");
        motorBikeGarageModel.add("engineAspiration", "Turbocharged");
        motorBikeGarageModel.add("transmissionType", "Automatic");
        when(garageService.addMotorbikeGarage(any(), any())).thenReturn(new GarageResponse());

        var response = mockMvc.perform(
                        post("/api/v1/garages/motorbike")
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .params(motorBikeGarageModel))
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        print())
                .andReturn();

        assertThat(response).isNotNull();
        assertThat(response.getResponse().getContentAsString()).contains("Garage vehicle added");
        verify(garageService).addMotorbikeGarage(any(), any());
    }

    @Test
    void allGarages() throws Exception {
        var response = mockMvc.perform(
                        get("/api/v1/garages")
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        print())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Garages fetched");
        verify(garageService).allGarages(anyInt());
    }

    @Test
    void getGarageById() throws Exception {
        when(garageService.getGarageById(anyString())).thenReturn(new GarageResponse());

        var garageId = "10e468b3-7b93-44dd-91de-b03d85d988fb";
        var response = mockMvc.perform(
                        get("/api/v1/garages/" + garageId)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        print())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Found garage");
        verify(garageService).getGarageById(anyString());
    }

    @Test
    void updateVehicleGarage() throws Exception {
        MultiValueMap<String, String> vehicleGarageModel = new LinkedMultiValueMap<>();
        vehicleGarageModel.add("vehicleMake", "Porsche");
        vehicleGarageModel.add("vehicleModel", "Dakar");
        vehicleGarageModel.add("name", "Porsche 911 Dakar 2024");
        vehicleGarageModel.add("description", "Porsche 911 Dakar planning to go on a world tour with it");
        vehicleGarageModel.add("buyingPrice", String.valueOf(20_0000_000.00));
        vehicleGarageModel.add("previousOwnersCount", String.valueOf(1));
        vehicleGarageModel.add("acceleration", String.valueOf(3));
        vehicleGarageModel.add("bodyType", "Coupe");
        vehicleGarageModel.add("driveTrain", "Rwd");
        vehicleGarageModel.add("fuelType", "Petrol");
        vehicleGarageModel.add("engineAspiration", "Turbocharged");
        vehicleGarageModel.add("engineLayout", "V6");
        vehicleGarageModel.add("enginePosition", "Rear");
        vehicleGarageModel.add("enginePower", String.valueOf(600));
        vehicleGarageModel.add("mileage", String.valueOf(10000));
        vehicleGarageModel.add("torque", String.valueOf(589));
        vehicleGarageModel.add("topSpeed", String.valueOf(340));
        vehicleGarageModel.add("transmissionType", "Manual");
        when(garageService.updateVehicleGarage(any(), anyString(), any())).thenReturn(new GarageResponse());

        var garageId = "10e468b3-7b93-44dd-91de-b03d85d988fb";
        var response = mockMvc.perform(
                        put("/api/v1/garages/vehicle/" + garageId)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .params(vehicleGarageModel))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        print())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Garage updated");
        verify(garageService).updateVehicleGarage(any(), anyString(), any());
    }

    @Test
    void updateMotorbikeGarage() throws Exception {
        var motorBikeGarageModel = new LinkedMultiValueMap<String, String>();

        motorBikeGarageModel.add("name", "Ducati");
        motorBikeGarageModel.add("description", "Duper fast and racy");
        motorBikeGarageModel.add("buyingPrice", String.valueOf(12000));
        motorBikeGarageModel.add("previousOwnersCount", String.valueOf(1));
        motorBikeGarageModel.add("acceleration", String.valueOf(3));
        motorBikeGarageModel.add("mileage", String.valueOf(2000));
        motorBikeGarageModel.add("topSpeed", String.valueOf(300));
        motorBikeGarageModel.add("enginePower", String.valueOf(120));
        motorBikeGarageModel.add("torque", String.valueOf(90));
        motorBikeGarageModel.add("motorbikeMake", "Ducati");
        motorBikeGarageModel.add("motorbikeModel", "Ninja");
        motorBikeGarageModel.add("motorbikeCategory", "Sports");
        motorBikeGarageModel.add("fuelType", "Petrol");
        motorBikeGarageModel.add("engineAspiration", "Turbocharged");
        motorBikeGarageModel.add("transmissionType", "Automatic");
        when(garageService.updateMotorbikeGarage(any(), anyString(), any())).thenReturn(new GarageResponse());

        var motorbikeId = "10e468b3-7b93-44dd-91de-b03d85d988fb";
        var response = mockMvc.perform(
                        put("/api/v1/garages/motorbike/" + motorbikeId)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .params(motorBikeGarageModel))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        print())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Motorbike updated");
        verify(garageService).updateMotorbikeGarage(any(), anyString(), any());
    }

    @Test
    void garagesByCategory() throws Exception {
        var category = "Vehicle";
        var response = mockMvc.perform(
                        get("/api/v1/garages/category/" + category)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        print())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Garage updated");
        verify(garageService).garagesByCategory(anyString(), anyInt());
    }

    @Test
    void searchGarages() throws Exception {
        var searchParams = new LinkedMultiValueMap<String, String>();
        searchParams.add("name", "Ducati");

        var response = mockMvc.perform(
                        get("/api/v1/garages/vehicles/search")
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .params(searchParams))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        print())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Vehicle garages searched");
        verify(garageService).searchGarages(anyInt(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void searchMotorbikes() throws Exception {
        var searchParams = new LinkedMultiValueMap<String, String>();
        searchParams.add("name", "Ducati");

        var response = mockMvc.perform(
                        get("/api/v1/garages/motorbikes/search")
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .params(searchParams))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        print())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Motorbike garages searched");
        verify(garageService).searchMotorbikeGarages(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void deleteGarage() throws Exception {
        var id = "id";

        var response = mockMvc.perform(
                        delete("/api/v1/garages/" + id)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        print())
                .andReturn();

        assertThat(response.getResponse().getContentAsString()).contains("Garage deleted");
        verify(garageService).deleteGarage(anyString());
    }
}
