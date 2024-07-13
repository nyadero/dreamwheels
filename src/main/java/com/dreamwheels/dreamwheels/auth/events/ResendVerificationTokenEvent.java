package com.dreamwheels.dreamwheels.auth.events;

import com.dreamwheels.dreamwheels.users.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ResendVerificationTokenEvent extends ApplicationEvent {
    private User user;
    private String applicationUrl;

    public ResendVerificationTokenEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
