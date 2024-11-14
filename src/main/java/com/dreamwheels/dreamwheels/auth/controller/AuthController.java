package com.dreamwheels.dreamwheels.auth.controller;

import com.dreamwheels.dreamwheels.auth.dtos.*;
import com.dreamwheels.dreamwheels.auth.entity.PasswordResetToken;
import com.dreamwheels.dreamwheels.auth.entity.VerificationToken;
import com.dreamwheels.dreamwheels.auth.response.SigninResponse;
import com.dreamwheels.dreamwheels.auth.service.AuthService;
import com.dreamwheels.dreamwheels.configuration.exceptions.ValidationException;
import com.dreamwheels.dreamwheels.configuration.responses.Data;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import com.dreamwheels.dreamwheels.users.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<GarageApiResponse<User>> signup(
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
        User registeredUser = authenticationService.registerUser(registerRequest, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new GarageApiResponse<>(new Data<>(registeredUser), "User registered", ResponseType.SUCCESS)
        );
    }

    // verify email registration
    @Operation(
            summary = "verify user registration",
            description = "verify user email after registration"
    )
    @GetMapping("/verify-registration")
    public ResponseEntity<GarageApiResponse<Void>>verifyRegistration(@RequestParam("token") String token){
        authenticationService.validateVerificationToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(null, "Your email has been verified. Proceed to login", ResponseType.SUCCESS)
        );
    }

    // regenerate verification token
    @Operation(
            summary = "regenerate user verification token",
            description = "regenerate user verification token"
    )
    @GetMapping("/resend-verification-token")
    public ResponseEntity<GarageApiResponse<Void>> resendVerificationToken(
            @RequestParam("token") String oldToken,
            HttpServletRequest request
    ){
        authenticationService.generateNewToken(oldToken, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(null, "new verification token sent to your email", ResponseType.SUCCESS)
        );
    }

    // sign-in user
    @Operation(
            summary = "sigin ",
            description = "sign in user"
    )
    @PostMapping("/signin")
    public ResponseEntity<GarageApiResponse<SigninResponse>> signinUser(
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
        SigninResponse signinResponse = authenticationService.signinUser(signinRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(new Data<>(signinResponse), "Sign in successful", ResponseType.SUCCESS)
        );
    }

    // forgot password
    @Operation(
            summary = "forgot password",
            description = "send forgot password request"
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<GarageApiResponse<Void>> forgotPassword(
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
        authenticationService.forgotPassword(forgotPasswordRequest, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(null, "Password reset token sent to your email", ResponseType.SUCCESS)
        );
    }

    // reset password
    @Operation(
            summary = "reset password",
            description = "reset password"
    )
    @PostMapping("/reset-password")
    public ResponseEntity<GarageApiResponse<Void>>resetPassword(
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
        authenticationService.resetPassword(token, resetPasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(null, "Password has been reset", ResponseType.SUCCESS)
        );
    }

    //    update password
    @Operation(
            summary = "update password",
            description = "updates user password"
    )
    @PreAuthorize("isAuthenticated") // access authenticated user details in service implementation
    @PutMapping("/update-password")
    public ResponseEntity<GarageApiResponse<Void>> updatePassword(
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
        authenticationService.updatePassword(updatePasswordModel);
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(null, "Password has been updated", ResponseType.SUCCESS)
        );
    }

    // delete account
    @Operation(
            summary = "delete account",
            description = "delete your account and all info"
    )
    @PreAuthorize("isAuthenticated")
    @PutMapping("/delete")
    public ResponseEntity<GarageApiResponse<Void>> deleteAccount(){
        authenticationService.deleteAccount();
        return ResponseEntity.status(HttpStatus.OK).body(
                new GarageApiResponse<>(null, "Account deleted successfully", ResponseType.SUCCESS)
        );
    }

}
