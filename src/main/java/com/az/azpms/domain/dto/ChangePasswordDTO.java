package com.az.azpms.domain.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {

    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

}
