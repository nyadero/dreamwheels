package com.dreamwheels.dreamwheels.users.serviceimpl;

import com.dreamwheels.dreamwheels.configuration.adapters.CustomPageAdapter;
import com.dreamwheels.dreamwheels.configuration.exceptions.EntityNotFoundException;
import com.dreamwheels.dreamwheels.configuration.middleware.TryCatchAnnotation;
import com.dreamwheels.dreamwheels.configuration.responses.CustomPageResponse;
import com.dreamwheels.dreamwheels.users.adapters.UserAdapter;
import com.dreamwheels.dreamwheels.users.dtos.UserDto;
import com.dreamwheels.dreamwheels.users.entity.User;
import com.dreamwheels.dreamwheels.users.repository.UserRepository;
import com.dreamwheels.dreamwheels.users.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final int PAGE_SIZE = 20;
    private final UserRepository userRepository;
    private final UserAdapter userAdapter;
    private final CustomPageAdapter<UserDto, UserDto> customPageAdapter;

    public UserServiceImpl(UserRepository userRepository, UserAdapter userAdapter, CustomPageAdapter<UserDto, UserDto> customPageAdapter) {
        this.userRepository = userRepository;
        this.userAdapter = userAdapter;
        this.customPageAdapter = customPageAdapter;
    }

    @Override
    @TryCatchAnnotation
    public CustomPageResponse<UserDto> allUsers(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, sort);
        Page<UserDto> users = userRepository.findAll(pageRequest).map(userAdapter::toBusiness);
        return customPageAdapter.toBusiness(users);
    }

    @Override
    @TryCatchAnnotation
    public UserDto userById(String id) {
        return userRepository.findById(id).map(userAdapter::toBusiness).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
