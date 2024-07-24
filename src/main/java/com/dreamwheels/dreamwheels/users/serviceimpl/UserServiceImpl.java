package com.dreamwheels.dreamwheels.users.serviceimpl;

import com.dreamwheels.dreamwheels.configuration.exceptions.EntityNotFoundException;
import com.dreamwheels.dreamwheels.configuration.middleware.TryCatchAnnotation;
import com.dreamwheels.dreamwheels.configuration.responses.Data;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import com.dreamwheels.dreamwheels.users.entity.User;
import com.dreamwheels.dreamwheels.users.repository.UserRepository;
import com.dreamwheels.dreamwheels.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> allUsers() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        List<User> users = userRepository.findAll(sort);
        return new ResponseEntity<>(new GarageApiResponse<>(new Data<>(users), "Found " + users.size() + " users", ResponseType.SUCCESS), HttpStatus.OK);
    }

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> userById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new ResponseEntity<>(new GarageApiResponse<>(new Data<>(user), "Found user", ResponseType.SUCCESS), HttpStatus.OK);
    }
}
