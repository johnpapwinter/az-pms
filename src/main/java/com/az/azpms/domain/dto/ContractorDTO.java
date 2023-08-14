package com.az.azpms.domain.dto;

import lombok.Data;

@Data
public class ContractorDTO {

    private Long id;
    private String name;
    private String description;
    private String email;
    private String phone1;
    private String phone2;
    private String address;
    private String city;
    private String country;

}
