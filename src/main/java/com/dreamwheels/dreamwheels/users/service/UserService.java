package com.dreamwheels.dreamwheels.users.service;

import com.dreamwheels.dreamwheels.configuration.responses.CustomPageResponse;
import com.dreamwheels.dreamwheels.users.dtos.UserResponse;

public interface UserService {
    CustomPageResponse<UserResponse> allUsers(int page);

    UserResponse userById(String id);
}
