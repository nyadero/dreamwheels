package com.dreamwheels.dreamwheels.auth.eventlisteners;

import com.dreamwheels.dreamwheels.auth.events.RegistrationCompleteEvent;
import com.dreamwheels.dreamwheels.auth.service.AuthService;
import com.dreamwheels.dreamwheels.users.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Autowired
    private AuthService authenticationService;

//    @Autowired
//    private EmailSender emailSender;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // create the verification token
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        authenticationService.saveVerificationToken(user, token);
        // send email
//        sendEmail(); // method to send email
        String url = event.getApplicationUrl() + "/api/v1/auth/verify-registration?token=" + token;
//        emailSender.sendEmail(user.getEmail(), buildEmail(user.getName(), url));
        log.info("Click the link below to verify your email " + url);
    }

    private String buildEmail(String name, String url) {
        return "Click the link below to verify your email " + url;
    }

}
