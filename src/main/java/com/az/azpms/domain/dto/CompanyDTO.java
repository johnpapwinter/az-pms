package com.az.azpms.domain.dto;


import lombok.Data;

@Data
public class CompanyDTO {
    private Long id;
    private String title;
    private String description;
    private String address;
    private String city;
    private String country;

}
