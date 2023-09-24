package com.az.azpms.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompanyDTO {

    private Long id;
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String address;
    @NotBlank
    private String city;
    @NotBlank
    private String country;

}
