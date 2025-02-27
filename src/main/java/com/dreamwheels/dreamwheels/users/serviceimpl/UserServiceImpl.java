package com.dreamwheels.dreamwheels.users.serviceimpl;

import com.dreamwheels.dreamwheels.configuration.adapters.CustomPageAdapter;
import com.dreamwheels.dreamwheels.configuration.exceptions.EntityNotFoundException;
import com.dreamwheels.dreamwheels.configuration.middleware.TryCatchAnnotation;
import com.dreamwheels.dreamwheels.configuration.responses.CustomPageResponse;
import com.dreamwheels.dreamwheels.users.adapters.UserAdapter;
import com.dreamwheels.dreamwheels.users.dtos.UserResponse;
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
    private final CustomPageAdapter<UserResponse, UserResponse> customPageAdapter;

    public UserServiceImpl(UserRepository userRepository, UserAdapter userAdapter, CustomPageAdapter<UserResponse, UserResponse> customPageAdapter) {
        this.userRepository = userRepository;
        this.userAdapter = userAdapter;
        this.customPageAdapter = customPageAdapter;
    }

    @Override
    @TryCatchAnnotation
    public CustomPageResponse<UserResponse> allUsers(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, sort);
        Page<UserResponse> users = userRepository.findAll(pageRequest).map(userAdapter::toBusiness);
        return customPageAdapter.toBusiness(users);
    }

    @Override
    @TryCatchAnnotation
    public UserResponse userById(String id) {
        return userRepository.findById(id).map(userAdapter::toBusiness).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
