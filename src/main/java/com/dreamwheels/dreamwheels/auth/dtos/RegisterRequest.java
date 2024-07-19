package com.dreamwheels.dreamwheels.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    @NotEmpty(message = "Name is required field")
    private String name;

    @NotEmpty(message = "username is required")
    private String userName;

    @NotEmpty()
    @Email(message = "this is not an email")
    private String email;

    @NotEmpty()
//    @Min(value = 6)
//    @Max(15)
    public String password;

    @NotEmpty()
//    @Min(6)
//    @Max(15)
    public String confirmPassword;
}