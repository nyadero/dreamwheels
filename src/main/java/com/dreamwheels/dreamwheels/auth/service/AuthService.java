package com.dreamwheels.dreamwheels.auth.service;

import com.dreamwheels.dreamwheels.auth.dtos.*;
import com.dreamwheels.dreamwheels.auth.entity.PasswordResetToken;
import com.dreamwheels.dreamwheels.auth.entity.VerificationToken;
import com.dreamwheels.dreamwheels.auth.response.SigninResponse;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.users.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<GarageApiResponse<User>> registerUser(RegisterRequest registerRequest, HttpServletRequest request);

    ResponseEntity<GarageApiResponse<User>> validateVerificationToken(String token);

    ResponseEntity<GarageApiResponse<VerificationToken>> generateNewToken(String oldToken, HttpServletRequest request);

    ResponseEntity<GarageApiResponse<User>> forgotPassword(ForgotPasswordRequest email, HttpServletRequest request);

    ResponseEntity<GarageApiResponse<PasswordResetToken>> resetPassword(String token, ResetPasswordRequest resetPasswordRequest);

    ResponseEntity<GarageApiResponse<SigninResponse>> signinUser(SigninRequest signinRequest);

    ResponseEntity<GarageApiResponse<User>> updatePassword(UpdatePasswordRequest updatePasswordModel);

    ResponseEntity<GarageApiResponse<Void>> deleteAccount();

    void saveVerificationToken(User user, String token);

    void saveResetPasswordToken(User user, String token);
}
