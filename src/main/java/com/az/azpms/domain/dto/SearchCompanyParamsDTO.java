package com.az.azpms.domain.dto;

import lombok.Data;

@Data
public class SearchCompanyParamsDTO {

    private String title;
    private String address;
    private String city;
    private String country;

}
