package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.RightName;
import lombok.Data;

import java.util.List;

@Data
public class JwtResponseDTO {

    private String token;
    private String username;
    private String email;
    private Long id;
    private List<RightName> rights;

}
