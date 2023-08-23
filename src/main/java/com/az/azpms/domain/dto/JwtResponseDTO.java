package com.az.azpms.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponseDTO {

    private String token;
    private String username;
    private String email;
    private List<String> rights;

}
