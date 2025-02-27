package com.dreamwheels.dreamwheels.users.controller;

import com.dreamwheels.dreamwheels.configuration.responses.CustomPageResponse;
import com.dreamwheels.dreamwheels.configuration.responses.Data;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import com.dreamwheels.dreamwheels.users.dtos.UserResponse;
import com.dreamwheels.dreamwheels.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //    all users
    @GetMapping
    public ResponseEntity<GarageApiResponse<CustomPageResponse<UserResponse>>> allUsers(
            @RequestParam(name = "page", defaultValue = "0") int page
    ){
        CustomPageResponse<UserResponse> users = userService.allUsers(page);
        return ResponseEntity.ok(new GarageApiResponse<>(new Data<>(users), "Users fetched", ResponseType.SUCCESS));
    }

//    user by id
    @GetMapping("/{id}")
    public ResponseEntity<GarageApiResponse<UserResponse>> userById(
            @PathVariable("id") String id
    ){
        UserResponse user = userService.userById(id);
        return ResponseEntity.ok(new GarageApiResponse<>(new Data<>(user), "User fetched", ResponseType.SUCCESS));
    }

}
