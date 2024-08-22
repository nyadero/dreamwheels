package com.dreamwheels.dreamwheels.auth.service;

import com.dreamwheels.dreamwheels.auth.dtos.*;
import com.dreamwheels.dreamwheels.auth.entity.VerificationToken;
import com.dreamwheels.dreamwheels.auth.response.SigninResponse;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.users.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    User registerUser(RegisterRequest registerRequest, HttpServletRequest request);

    void validateVerificationToken(String token);

    VerificationToken generateNewToken(String oldToken, HttpServletRequest request);

    void forgotPassword(ForgotPasswordRequest email, HttpServletRequest request);

    void resetPassword(String token, ResetPasswordRequest resetPasswordRequest);

    SigninResponse signinUser(SigninRequest signinRequest);

    void updatePassword(UpdatePasswordRequest updatePasswordModel);

    ResponseEntity<GarageApiResponse<Void>> deleteAccount();

    void saveVerificationToken(User user, String token);

    void saveResetPasswordToken(User user, String token);
}
