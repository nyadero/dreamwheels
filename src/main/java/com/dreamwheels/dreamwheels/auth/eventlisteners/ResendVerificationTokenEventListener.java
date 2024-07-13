package com.dreamwheels.dreamwheels.auth.eventlisteners;

import com.dreamwheels.dreamwheels.auth.events.ResendVerificationTokenEvent;
import com.dreamwheels.dreamwheels.auth.service.AuthService;
import com.dreamwheels.dreamwheels.users.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class ResendVerificationTokenEventListener implements ApplicationListener<ResendVerificationTokenEvent> {
    @Autowired
    private AuthService authenticationService;

    @Override
    public void onApplicationEvent(ResendVerificationTokenEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        authenticationService.saveVerificationToken(user, token);
        String url = event.getApplicationUrl() + "/api/v1/auth/verify-registration?token="+token;
        log.info("A new verification token has been sent to your email " + url);

    }
}
