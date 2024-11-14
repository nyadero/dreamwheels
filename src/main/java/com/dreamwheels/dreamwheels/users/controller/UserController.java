package com.dreamwheels.dreamwheels.users.controller;

import com.dreamwheels.dreamwheels.configuration.responses.CustomPageResponse;
import com.dreamwheels.dreamwheels.configuration.responses.Data;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import com.dreamwheels.dreamwheels.users.dtos.UserDto;
import com.dreamwheels.dreamwheels.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<GarageApiResponse<CustomPageResponse<UserDto>>> allUsers(
            @RequestParam(name = "page", defaultValue = "0") int page
    ){
        CustomPageResponse<UserDto> users = userService.allUsers(page);
        return ResponseEntity.ok(new GarageApiResponse<>(new Data<>(users), "Users fetched", ResponseType.SUCCESS));
    }

//    user by id
    @GetMapping("/{id}")
    public ResponseEntity<GarageApiResponse<UserDto>> userById(
            @PathVariable("id") String id
    ){
        UserDto user = userService.userById(id);
        return ResponseEntity.ok(new GarageApiResponse<>(new Data<>(user), "User fetched", ResponseType.SUCCESS));
    }

}
