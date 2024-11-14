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
import com.dreamwheels.dreamwheels.configuration.exceptions.*;
import com.dreamwheels.dreamwheels.configuration.middleware.TryCatchAnnotation;
import com.dreamwheels.dreamwheels.configuration.responses.Data;
import com.dreamwheels.dreamwheels.configuration.responses.GarageApiResponse;
import com.dreamwheels.dreamwheels.configuration.responses.ResponseType;
import com.dreamwheels.dreamwheels.configuration.security.jwt.JwtUtils;
import com.dreamwheels.dreamwheels.users.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Calendar;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            AuthRepository authRepository,
            VerificationTokenRepository verificationTokenRepository,
            PasswordResetTokenRepository passwordResetTokenRepository,
            PasswordEncoder passwordEncoder,
            ApplicationEventPublisher applicationEventPublisher,
            JwtUtils jwtUtils,
            AuthenticationManager authenticationManager
    ) {
        this.authRepository = authRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.applicationEventPublisher = applicationEventPublisher;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @TryCatchAnnotation
    public User registerUser(RegisterRequest registerRequest, HttpServletRequest request) {
//        find if user is already registered
        if (authRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EntityExistsException("Email already registered");
        }
        // compare passwords
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
           throw new PasswordsNotMatchingException("Passwords do not match");
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
        return savedUser;
    }

    @Override
    @TryCatchAnnotation
    public void validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(()->new EntityNotFoundException("Invalid email verification token"));
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(verificationToken);
            throw new TokenExpiredException("Email verification token has expired");
        }
        user.setEnabled(true);
        authRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
    }

    @Override
    @TryCatchAnnotation
    public VerificationToken generateNewToken(String oldToken, HttpServletRequest request) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken).orElseThrow(()->new EntityNotFoundException("Invalid email verification token"));
        verificationTokenRepository.delete(verificationToken);
        applicationEventPublisher.publishEvent(new ResendVerificationTokenEvent(verificationToken.getUser(), applicationUrl(request)));
        return verificationToken;
    }

    @Override
    @TryCatchAnnotation
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest, HttpServletRequest request) {
        User user = authRepository.findByEmail(forgotPasswordRequest.getEmail()).orElseThrow(() -> new EntityNotFoundException("The entered email address is incorrect"));
        applicationEventPublisher.publishEvent(new ForgotPasswordEvent(user, applicationUrl(request)));
    }


    @Override
    @TryCatchAnnotation
    public void resetPassword(String token, ResetPasswordRequest resetPasswordRequest) {
        // check if token exists
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new EntityNotFoundException("Invalid password reset token"));
        // compare passwords
        if(!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmNewPassword())){
            throw new PasswordsNotMatchingException("Passwords do not match");
        }
        // check token expiry
        Calendar calendar = Calendar.getInstance();
        if ((passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            throw new TokenExpiredException("Password reset token has expired");
        }
        // save user
        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        authRepository.save(user);
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    @Override
    @TryCatchAnnotation
    public SigninResponse signinUser(SigninRequest signinRequest)  {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword());
        log.info("isAuthenticated {}", usernamePasswordAuthenticationToken.getDetails());
        if (usernamePasswordAuthenticationToken.getDetails() != null){
            log.error("Incorrect credentials");
            throw new BadCredentialsException("Check your credentials and try again");
        }
        log.info("user {}", usernamePasswordAuthenticationToken);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(!authentication.isAuthenticated()){
            throw new BadCredentialsException("Incorrect credentials2");
        }
        System.out.println(authentication.getPrincipal());
        User user1 = (User) authentication.getPrincipal();
        var jwtToken = jwtUtils.generateJwtToken(user1);
        log.info("generated token  {}", jwtToken);
        return SigninResponse.builder()
                .id(user1.getId())
                .name(user1.getName())
                .userName(user1.getUsername())
                .email(user1.getEmail())
                .jwtToken(jwtToken)
                .build();
    }

    @Override
    @TryCatchAnnotation
    public void updatePassword(UpdatePasswordRequest updatePasswordModel) {
        // compare current password with user password
        if(!passwordEncoder.matches(updatePasswordModel.getCurrentPassword(), authenticatedUser().getPassword())){
            throw new PasswordsNotMatchingException("Passwords do not match");
        }
        // compare new password and confirm password
        if (!updatePasswordModel.getNewPassword().equals(updatePasswordModel.getConfirmNewPassword())){
            throw new PasswordsNotMatchingException("Passwords do not match");
        }

        authenticatedUser().setPassword(passwordEncoder.encode(updatePasswordModel.getNewPassword()));
        authRepository.save(authenticatedUser());
    }


    @Override
    @TryCatchAnnotation
//    @Transactional
    public ResponseEntity<GarageApiResponse<Void>> deleteAccount() {
        User user = authRepository.findById(authenticatedUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        authRepository.delete(user);
        return new ResponseEntity<>(new GarageApiResponse<>(new Data<>(null), "User account removed", ResponseType.SUCCESS), HttpStatus.OK);
    }

    private User authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
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
