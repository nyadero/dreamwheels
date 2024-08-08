package com.dreamwheels.dreamwheels.likes.service;

import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.likes.enums.LikeAction;
import org.springframework.http.ResponseEntity;

public interface LikeService {
    ResponseEntity<GarageApiResponse<LikeAction>> likeGarage(String garageId);
}
