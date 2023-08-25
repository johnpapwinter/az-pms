package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.RightName;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {

    private Long id;
    @NotNull
    private String roleName;
    private Boolean active;
    private List<RightName> rights;
    private List<String> users;

}
