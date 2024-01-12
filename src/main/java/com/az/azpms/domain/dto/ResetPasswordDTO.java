package com.az.azpms.domain.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {

    private String resetToken;
    private String password;
    private String passwordConfirmation;

}
