package com.dreamwheels.dreamwheels.auth.serviceimpl;

import com.dreamwheels.dreamwheels.auth.dtos.*;
import com.dreamwheels.dreamwheels.auth.entity.PasswordResetToken;
import com.dreamwheels.dreamwheels.auth.entity.VerificationToken;
import com.dreamwheels.dreamwheels.auth.enums.Role;
import com.dreamwheels.dreamwheels.auth.events.ForgotPasswordEvent;
import com.dreamwheels.dreamwheels.auth.events.RegistrationCompleteEvent;
import com.dreamwheels.dreamwheels.auth.events.ResendVerificationTokenEvent;
import com.dreamwheels.dreamwheels.auth.repository.AuthRepository;
import com.dreamwheels.dreamwheels.auth.repository.PasswordResetTokenRepository;
import com.dreamwheels.dreamwheels.auth.repository.VerificationTokenRepository;
import com.dreamwheels.dreamwheels.auth.response.SigninResponse;
import com.dreamwheels.dreamwheels.auth.service.AuthService;
import com.dreamwheels.dreamwheels.configuration.exceptions.EntityNotFoundException;
import com.dreamwheels.dreamwheels.configuration.middleware.TryCatchAnnotation;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import com.dreamwheels.dreamwheels.configuration.security.jwt.JwtUtils;
import com.dreamwheels.dreamwheels.users.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> registerUser(RegisterRequest registerRequest, HttpServletRequest request) {
//        find if user is already registered
        if (authRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>(new GarageApiResponse(null, "Email is already taken", ResponseType.ERROR), HttpStatus.OK);
        }
        if (authRepository.existsByUserName(registerRequest.getUserName())) {
            return new ResponseEntity<>(new GarageApiResponse(null, " username is already taken", ResponseType.ERROR), HttpStatus.OK);
        }
        // compare passwords
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return new ResponseEntity<>(new GarageApiResponse(null, "Passwords do not match", ResponseType.ERROR), HttpStatus.OK);
        }

        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .userName(registerRequest.getUserName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .isEnabled(false)
                .role(Role.USER)
                .build();
        var savedUser = authRepository.save(user);
//        String link = "http://localhost:8080/api/v1/registration/confirm?token=";
//        emailSender.sendEmail(savedUser.getEmail(), buildEmail(savedUser.getName(), link));
        // SEND EMAIL EVENT -should be sent after response is returned
        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(savedUser, applicationUrl(request)));
        return new ResponseEntity<>(new GarageApiResponse(savedUser, "User registered successfully", ResponseType.SUCCESS), HttpStatus.CREATED);
    }

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null){
            return new ResponseEntity<>(new GarageApiResponse(null, "invalid verification token. Ensure it is the correct one", ResponseType.ERROR), HttpStatus.BAD_REQUEST);
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(verificationToken);
            return new ResponseEntity<>(new GarageApiResponse(null, "Verification token has expired", ResponseType.ERROR), HttpStatus.BAD_REQUEST);
        }
        user.setEnabled(true);
        authRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
        return new ResponseEntity<>(new GarageApiResponse(null, "Verified successfully. Proceed to login", ResponseType.SUCCESS), HttpStatus.BAD_REQUEST);
    }

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> generateNewToken(String oldToken, HttpServletRequest request) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        verificationTokenRepository.delete(verificationToken);
        applicationEventPublisher.publishEvent(new ResendVerificationTokenEvent(verificationToken.getUser(), applicationUrl(request)));
        return new ResponseEntity<>(new GarageApiResponse(null, "A new verification token has been sent to " + verificationToken.getUser().getEmail(), ResponseType.SUCCESS), HttpStatus.OK);
    }

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> forgotPassword(ForgotPasswordRequest forgotPasswordRequest, HttpServletRequest request) {
        User user = authRepository.findByEmail(forgotPasswordRequest.getEmail());
        if(user == null){
            return new ResponseEntity<>(new GarageApiResponse(null, "Error: The email address could not be found", ResponseType.ERROR), HttpStatus.BAD_REQUEST);
        }
        applicationEventPublisher.publishEvent(new ForgotPasswordEvent(user, applicationUrl(request)));
        return new ResponseEntity<>(new GarageApiResponse(null, "A new password token has been sent to " + forgotPasswordRequest.getEmail(), ResponseType.SUCCESS), HttpStatus.OK);
    }


    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> resetPassword(String token, ResetPasswordRequest resetPasswordRequest) {
        // check if token exists
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        System.out.println(passwordResetToken);
//       // get user
        if(passwordResetToken == null){
            return new ResponseEntity<>(new GarageApiResponse(null, "Error: invalid password reset token", ResponseType.ERROR), HttpStatus.BAD_REQUEST);
        }
        // compare passwords
        if(!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmNewPassword())){
            return new ResponseEntity<>(new GarageApiResponse(null, "Error: Passwords do not match", ResponseType.ERROR), HttpStatus.BAD_REQUEST);
        }
        // check token expiry
        Calendar calendar = Calendar.getInstance();
        if ((passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            return new ResponseEntity<>(new GarageApiResponse(null, "Error: Your password reset token is expired", ResponseType.ERROR), HttpStatus.BAD_REQUEST);
        }
        // save user
        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        authRepository.save(user);
        passwordResetTokenRepository.delete(passwordResetToken);
        return new ResponseEntity<>(new GarageApiResponse(null, "Your password has been reset successfully", ResponseType.SUCCESS), HttpStatus.OK);
    }

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> signinUser(SigninRequest signinRequest)  {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword());
        System.out.println(usernamePasswordAuthenticationToken);
//        if (usernamePasswordAuthenticationToken){
//            return new ResponseEntity<>(new GarageApiResponse(null, "Incorrect credentials", ResponseType.ERROR), HttpStatus.BAD_REQUEST);
//        }
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(!authentication.isAuthenticated()){
            throw new BadCredentialsException("Incorrect credentials2");
        }
        System.out.println(authentication.getPrincipal());
        User user = authRepository.findByEmail(signinRequest.getEmail());
        if(user == null){
            return new ResponseEntity<>(new GarageApiResponse(null, "The email address could not be found", ResponseType.ERROR), HttpStatus.BAD_REQUEST);
        }
        var jwtToken = jwtUtils.generateJwtToken(user);
        System.out.println("generated token  " + jwtToken);
        SigninResponse signinResponse = SigninResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .userName(user.getUsername())
                .email(user.getEmail())
                .jwtToken(jwtToken)
                .build();
        return new ResponseEntity<>(new GarageApiResponse(signinResponse, "Successfully logged in", ResponseType.SUCCESS), HttpStatus.OK);
    }

    @Override
    @TryCatchAnnotation
    public ResponseEntity<GarageApiResponse> updatePassword(UpdatePasswordRequest updatePasswordModel) {
        System.out.println("user password " + authenticatedUser().getPassword());
        System.out.println(passwordEncoder.encode(updatePasswordModel.getCurrentPassword()));
        // compare current password with user password
        if(!passwordEncoder.matches(updatePasswordModel.getCurrentPassword(), authenticatedUser().getPassword())){
            return new ResponseEntity<>(new GarageApiResponse(null, "Passwords do not match", ResponseType.ERROR), HttpStatus.OK);
        }
        // compare new password and confirm password
        if (!updatePasswordModel.getNewPassword().equals(updatePasswordModel.getConfirmNewPassword())){
            return new ResponseEntity<>(new GarageApiResponse(null, "Passwords do not match", ResponseType.ERROR), HttpStatus.OK);
        }
        authenticatedUser().setPassword(passwordEncoder.encode(updatePasswordModel.getNewPassword()));
        return new ResponseEntity<>(new GarageApiResponse(authRepository.save(authenticatedUser()), "Password updated successfully", ResponseType.SUCCESS), HttpStatus.CREATED);
    }

    @Override
    @TryCatchAnnotation
//    @Transactional
    public ResponseEntity<GarageApiResponse> deleteAccount() {
        User user = authRepository.findById(authenticatedUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        System.out.println(user.getName());
//        authRepository.deleteById(user.getId());
        authRepository.delete(user);
        return new ResponseEntity<>(new GarageApiResponse(null, "User account removed", ResponseType.SUCCESS), HttpStatus.OK);
    }


    private User authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authRepository.findByEmail(authentication.getName());
    }

    public void saveVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    @TryCatchAnnotation
    public void saveResetPasswordToken(User user, String token) {
        // create new password reset token
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    // get url
    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
