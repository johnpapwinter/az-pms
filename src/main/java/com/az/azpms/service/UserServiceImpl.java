package com.az.azpms.service;

import com.az.azpms.domain.dto.*;
import com.az.azpms.domain.entities.AzUser;
import com.az.azpms.domain.entities.QAzUser;
import com.az.azpms.domain.entities.Right;
import com.az.azpms.domain.entities.Role;
import com.az.azpms.domain.enums.AzUserStatus;
import com.az.azpms.domain.enums.RightName;
import com.az.azpms.domain.exceptions.AzAlreadyExistsException;
import com.az.azpms.domain.exceptions.AzAuthException;
import com.az.azpms.domain.exceptions.AzErrorMessages;
import com.az.azpms.domain.exceptions.AzNotFoundException;
import com.az.azpms.domain.repository.AzUserRepository;
import com.az.azpms.domain.repository.RoleRepository;
import com.az.azpms.utils.Utils;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final AzUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final Utils utils;

    public UserServiceImpl(AzUserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           Utils utils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    @Transactional(readOnly = true)
    public Page<AzUserDTO> searchUsers(SearchAzUserParamsDTO dto, Pageable pageable) {
        QAzUser qAzUser = QAzUser.azUser;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (dto.getUsername() != null) {
            booleanBuilder.and(qAzUser.username.containsIgnoreCase(dto.getUsername()));
        }

        if (dto.getFirstname() != null) {
            booleanBuilder.and(qAzUser.firstname.containsIgnoreCase(dto.getFirstname()));
        }

        if (dto.getLastname() != null) {
            booleanBuilder.and(qAzUser.lastname.containsIgnoreCase(dto.getLastname()));
        }

        if (dto.getEmail() != null) {
            booleanBuilder.and(qAzUser.email.containsIgnoreCase(dto.getEmail()));
        }

        if (dto.getCity() != null) {
            booleanBuilder.and(qAzUser.city.containsIgnoreCase(dto.getCity()));
        }

        if (dto.getAddress() != null) {
            booleanBuilder.and(qAzUser.address.containsIgnoreCase(dto.getAddress()));
        }

        if (dto.getCountry() != null) {
            booleanBuilder.and(qAzUser.country.containsIgnoreCase(dto.getCountry()));
        }

        if (dto.getStatus() != null) {
            booleanBuilder.and(qAzUser.status.eq(dto.getStatus()));
        }

        return userRepository.findAll(booleanBuilder, pageable)
                .map(this::toUserDTO);
    }

    @Override
    @Transactional
    public AzUser generateResetPasswordToken(GenerateResetPasswordDTO dto) {
        AzUser azUser = userRepository.findAzUserByEmail(dto.getEmail()).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        String resetPasswordToken = UUID.randomUUID().toString();

        azUser.setResetToken(resetPasswordToken);
        userRepository.save(azUser);

        return azUser;
    }

    @Override
    @Transactional
    public AzUser resetPassword(ResetPasswordDTO dto) {
        AzUser azUser = userRepository.findAzUserByResetToken(dto.getResetToken()).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        comparePasswords(dto.getPassword(), dto.getPasswordConfirmation());

        azUser.setPassword(dto.getPassword());
        azUser.setResetToken(null);
        userRepository.save(azUser);

        return azUser;
    }


    private void comparePasswords(String password, String confirmationPassword) {
        if (!passwordEncoder.matches(confirmationPassword, password)) {
            throw new AzAuthException(AzErrorMessages.PASSWORDS_DO_NOT_MATCH.name());
        }
    }

    private AzUserDTO toUserDTO(AzUser user) {
        AzUserDTO dto = new AzUserDTO();
        utils.initModelMapperStrict().map(user, dto);
        List<RoleDTO> roleDTOList = user.getRoles()
                .stream()
                .map(this::toRoleDTO)
                .toList();
        dto.setRoles(roleDTOList);

        return dto;
    }

    private RoleDTO toRoleDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        utils.initModelMapperStrict().map(role, dto);
        List<RightName> rights = role.getRights()
                .stream()
                .map(Right::getName)
                .toList();
        dto.setRights(rights);

        return dto;
    }
}
