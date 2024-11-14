package com.dreamwheels.dreamwheels.users.adapters;

import com.dreamwheels.dreamwheels.configuration.adapters.EntityAdapter;
import com.dreamwheels.dreamwheels.users.dtos.UserDto;
import com.dreamwheels.dreamwheels.users.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements EntityAdapter<User, UserDto> {
    @Override
    public UserDto toBusiness(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
