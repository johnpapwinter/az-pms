package com.az.azpms.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContractorDTO {

    private Long id;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private String email;
    @NotNull
    private String phone1;
    private String phone2;
    @NotNull
    private String address;
    @NotNull
    private String city;
    @NotNull
    private String country;

}
