package com.dreamwheels.dreamwheels.users.controller;

import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

//    all users
    @GetMapping("")
    public ResponseEntity<GarageApiResponse> allUsers(){
        return userService.allUsers();
    }

//    user by id
    @GetMapping("/{id}")
    public ResponseEntity<GarageApiResponse> userById(
            @PathVariable("id") String id
    ){
        return userService.userById(id);
    }

}
