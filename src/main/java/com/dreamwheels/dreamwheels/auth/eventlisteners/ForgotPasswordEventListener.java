package com.dreamwheels.dreamwheels.auth.eventlisteners;

import com.dreamwheels.dreamwheels.auth.events.ForgotPasswordEvent;
import com.dreamwheels.dreamwheels.auth.service.AuthService;
import com.dreamwheels.dreamwheels.users.entity.User;
import jakarta.persistence.Cacheable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ForgotPasswordEventListener implements ApplicationListener<ForgotPasswordEvent> {
    @Autowired
    AuthService authenticationService;
    @Override
    public void onApplicationEvent(ForgotPasswordEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        authenticationService.saveResetPasswordToken(user, token);
        // send email
        String url = event.getApplicationUrl() + "/api/v1/auth/reset-password?token=" + token;
        log.info("A password rest link has been sent to your email " + url);
    }
}
