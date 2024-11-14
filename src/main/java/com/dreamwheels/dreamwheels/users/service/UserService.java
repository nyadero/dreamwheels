package com.dreamwheels.dreamwheels.users.service;

import com.dreamwheels.dreamwheels.configuration.responses.CustomPageResponse;
import com.dreamwheels.dreamwheels.users.dtos.UserDto;

public interface UserService {
    CustomPageResponse<UserDto> allUsers(int page);

    UserDto userById(String id);
}
