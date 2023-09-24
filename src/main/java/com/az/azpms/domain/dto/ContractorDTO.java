package com.az.azpms.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContractorDTO {

    private Long id;
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String phone1;
    private String phone2;
    @NotBlank
    private String address;
    @NotBlank
    private String city;
    @NotBlank
    private String country;

}
