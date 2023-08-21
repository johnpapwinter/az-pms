package com.az.azpms.domain.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompanyDTO {

    private Long id;
    @NotNull
    private String title;
    private String description;
    @NotNull
    private String address;
    @NotNull
    private String city;
    @NotNull
    private String country;

}
