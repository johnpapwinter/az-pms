package com.az.azpms.service;

import com.az.azpms.domain.dto.AzUserDTO;
import com.az.azpms.domain.dto.RegistrationDTO;
import com.az.azpms.domain.entities.AzUser;
import com.az.azpms.domain.entities.Role;
import com.az.azpms.domain.enums.AzUserStatus;
import com.az.azpms.domain.exceptions.AzAlreadyExistsException;
import com.az.azpms.domain.exceptions.AzAuthException;
import com.az.azpms.domain.exceptions.AzErrorMessages;
import com.az.azpms.domain.exceptions.AzNotFoundException;
import com.az.azpms.domain.repository.AzUserRepository;
import com.az.azpms.domain.repository.RoleRepository;
import com.az.azpms.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final AzUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Utils utils;

    public UserServiceImpl(AzUserRepository userRepository,
                           RoleRepository roleRepository,
                           Utils utils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.utils = utils;
    }

    @Override
    @Transactional
    public void createUser(RegistrationDTO dto) {
        userRepository.findAzUserByUsername(dto.getUsername()).ifPresent(
                azUser -> {
                    throw new AzAlreadyExistsException(AzErrorMessages.ENTITY_ALREADY_EXISTS.name());
                }
        );

        AzUser user = new AzUser();
        utils.initModelMapperStrict().map(dto, user);
        user.setStatus(AzUserStatus.INACTIVE);


        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(AzUserDTO dto) {
        AzUser user = userRepository.findById(dto.getId()).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        utils.initModelMapperStrict().map(dto, user);

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AzUserDTO findUserById(Long id) {
        AzUser user = userRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );

        return toUserDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AzUser findUserByUsername(String username) {
        return userRepository.findAzUserByUsername(username).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
    }

    @Override
    @Transactional
    public void changeUserStatus(Long id, AzUserStatus status) {
        AzUser user = userRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        user.setStatus(status);

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AzUserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::toUserDTO);
    }

    @Override
    @Transactional
    public void assignRolesToUser(Long userId, List<Long> roleIds) {
        AzUser user = userRepository.findById(userId).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        List<Role> roles = roleRepository.findAllByIdIn(roleIds);

        user.getRoles().clear();
        user.setRoles(roles);
    }

    @Override
    public void matchPasswords(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            throw new AzAuthException(AzErrorMessages.PASSWORDS_DO_NOT_MATCH.name());
        }
    }

    private AzUserDTO toUserDTO(AzUser user) {
        AzUserDTO dto = new AzUserDTO();
        utils.initModelMapperStrict().map(user, dto);

        return dto;
    }
}
