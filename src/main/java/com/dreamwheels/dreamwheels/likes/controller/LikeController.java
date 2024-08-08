package com.dreamwheels.dreamwheels.likes.controller;

import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.likes.enums.LikeAction;
import com.dreamwheels.dreamwheels.likes.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/{garageId}")
    @PreAuthorize("isAuthenticated")
    public ResponseEntity<GarageApiResponse<LikeAction>> likeGarage(
         @PathVariable("garageId") String garageId
    ){
        return likeService.likeGarage(garageId);
    }

}
