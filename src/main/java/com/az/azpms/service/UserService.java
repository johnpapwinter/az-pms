package com.az.azpms.service;

import com.az.azpms.domain.dto.AzUserDTO;
import com.az.azpms.domain.dto.RegistrationDTO;
import com.az.azpms.domain.entities.AzUser;
import com.az.azpms.domain.enums.AzUserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void createUser(RegistrationDTO dto);

    void updateUser(AzUserDTO dto);

    AzUserDTO findUserById(Long id);

    AzUser findUserByUsername(String username);

    void changeUserStatus(Long id, AzUserStatus status);

    Page<AzUserDTO> getAllUsers(Pageable pageable);


}
