package com.dreamwheels.dreamwheels.auth.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SigninResponse {
    private String id;
    private String name;
    private String email;
    private String avatar;
    private String userName;
    private String jwtToken;
}
