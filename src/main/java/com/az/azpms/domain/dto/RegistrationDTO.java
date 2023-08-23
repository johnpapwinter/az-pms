package com.az.azpms.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationDTO {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @Email
    private String email;
    @NotNull
    private String city;
    @NotNull
    private String address;
    @NotNull
    private String country;

}
