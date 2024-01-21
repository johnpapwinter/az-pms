package com.az.azpms.service;

import com.az.azpms.domain.dto.*;
import com.az.azpms.domain.entities.AzUser;
import com.az.azpms.domain.entities.AzUserPrincipal;
import com.az.azpms.domain.enums.AzUserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    void createUser(RegistrationDTO dto);

    void updateUser(AzUserDTO dto);

    AzUserDTO findUserById(Long id);

    AzUser findUserByUsername(String username);

    void changeUserStatus(Long id, AzUserStatus status);

    Page<AzUserDTO> getAllUsers(Pageable pageable);

    void assignRolesToUser(Long userId, List<Long> roleIds);

    void matchPasswords(String password, String passwordConfirmation);

    Page<AzUserDTO> searchUsers(SearchAzUserParamsDTO searchParamsDTO,  Pageable pageable);

    void changePassword(AzUserPrincipal userPrincipal, ChangePasswordDTO dto);

    AzUser generateResetPasswordToken(GenerateResetPasswordDTO dto);

    AzUser resetPassword(ResetPasswordDTO dto);

    void toggleUserStatus(AzUserDTO dto);

}
