package com.dreamwheels.dreamwheels.auth.service;

import com.dreamwheels.dreamwheels.auth.dtos.*;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.users.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<GarageApiResponse> registerUser(RegisterRequest registerRequest, HttpServletRequest request);

    ResponseEntity<GarageApiResponse> validateVerificationToken(String token);

    ResponseEntity<GarageApiResponse> generateNewToken(String oldToken, HttpServletRequest request);

    ResponseEntity<GarageApiResponse> forgotPassword(ForgotPasswordRequest email, HttpServletRequest request);

    ResponseEntity<GarageApiResponse> resetPassword(String token, ResetPasswordRequest resetPasswordRequest);

    ResponseEntity<GarageApiResponse> signinUser(SigninRequest signinRequest);

    ResponseEntity<GarageApiResponse> updatePassword(UpdatePasswordRequest updatePasswordModel);

    ResponseEntity<GarageApiResponse> deleteAccount();

    void saveVerificationToken(User user, String token);

    void saveResetPasswordToken(User user, String token);
}
