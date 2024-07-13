package com.dreamwheels.dreamwheels.auth.controller;

import com.dreamwheels.dreamwheels.auth.dtos.*;
import com.dreamwheels.dreamwheels.auth.service.AuthService;
import com.dreamwheels.dreamwheels.configuration.exceptions.ValidationException;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authenticationService;

    //    method to register user
    @Operation(
            summary = "signup",
            description = "sign up user"
    )
    @PostMapping("/signup")
    public ResponseEntity<GarageApiResponse> signup(
            @Valid @RequestBody RegisterRequest registerRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
        return authenticationService.registerUser(registerRequest, request);
    }

    // verify email registration
    @Operation(
            summary = "verify user registration",
            description = "verify user email after registration"
    )
    @GetMapping("/verify-registration")
    public ResponseEntity<GarageApiResponse>verifyRegistration(@RequestParam("token") String token){
        return authenticationService.validateVerificationToken(token);
    }

    // regenerate verification token
    @Operation(
            summary = "regenerate user verification token",
            description = "regenerate user verification token"
    )
    @GetMapping("/resend-verification-token")
    public ResponseEntity<GarageApiResponse> resendVerificationToken(
            @RequestParam("token") String oldToken,
            HttpServletRequest request
    ){
        return authenticationService.generateNewToken(oldToken, request);
    }

    // sign-in user
    @Operation(
            summary = "sigin ",
            description = "sign in user"
    )
    @PostMapping("/signin")
    public ResponseEntity<GarageApiResponse> signinUser(
            @Valid @RequestBody SigninRequest signinRequest,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
        return authenticationService.signinUser(signinRequest);
    }

    // forgot password
    @Operation(
            summary = "forgot password",
            description = "send forgot password request"
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<GarageApiResponse> forgotPassword(
            @RequestBody ForgotPasswordRequest forgotPasswordRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
        return authenticationService.forgotPassword(forgotPasswordRequest, request);
    }

    // reset password
    @Operation(
            summary = "reset password",
            description = "reset password"
    )
    @PostMapping("/reset-password")
    public ResponseEntity<GarageApiResponse>resetPassword(
            @RequestParam String token,
            @RequestBody ResetPasswordRequest resetPasswordRequest,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
        return authenticationService.resetPassword(token, resetPasswordRequest);
    }

    //    update password
    @Operation(
            summary = "update password",
            description = "updates user password"
    )
    @PreAuthorize("isAuthenticated()") // access authenticated user details in service implementation
    @PutMapping("/update-password")
    public ResponseEntity<GarageApiResponse> updatePassword(
            @RequestBody UpdatePasswordRequest updatePasswordModel,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
        return authenticationService.updatePassword(updatePasswordModel);
    }

    // delete account
    public ResponseEntity<GarageApiResponse> deleteAccount(

    ){
        return null;
    }

}
