package com.az.azpms.domain.dto;

import lombok.Data;

@Data
public class RegistrationDTO {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String city;
    private String address;
    private String country;

}
