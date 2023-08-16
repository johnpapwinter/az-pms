package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.AzUserStatus;
import lombok.Data;

@Data
public class AzUserDTO {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String city;
    private String address;
    private String country;
    private AzUserStatus status;

}
