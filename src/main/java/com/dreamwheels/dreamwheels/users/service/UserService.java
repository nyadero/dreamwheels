package com.dreamwheels.dreamwheels.users.service;

import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<GarageApiResponse> allUsers();

    ResponseEntity<GarageApiResponse> userById(String id);
}
