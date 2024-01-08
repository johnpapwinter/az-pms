package com.az.azpms.service;

import com.az.azpms.domain.dto.RoleDTO;
import com.az.azpms.domain.dto.SearchRoleParamsDTO;
import com.az.azpms.domain.entities.AzUser;
import com.az.azpms.domain.entities.QRole;
import com.az.azpms.domain.entities.Right;
import com.az.azpms.domain.entities.Role;
import com.az.azpms.domain.enums.RightName;
import com.az.azpms.domain.exceptions.AzAlreadyExistsException;
import com.az.azpms.domain.exceptions.AzErrorMessages;
import com.az.azpms.domain.exceptions.AzNotFoundException;
import com.az.azpms.domain.repository.RightRepository;
import com.az.azpms.domain.repository.RoleRepository;
import com.az.azpms.utils.Utils;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RightRepository rightRepository;
    private final Utils utils;

    public RoleServiceImpl(RoleRepository roleRepository,
                           RightRepository rightRepository,
                           Utils utils) {
        this.roleRepository = roleRepository;
        this.rightRepository = rightRepository;
        this.utils = utils;
    }

    @Override
    @Transactional
    public void createRole(RoleDTO dto) {
        roleRepository.findRoleByRoleName(dto.getRoleName()).ifPresent(
                role -> {
                    throw new AzAlreadyExistsException(AzErrorMessages.ENTITY_ALREADY_EXISTS.name());
                }
        );
        List<Right> rights = rightRepository.findAllByNameIn(dto.getRights());

        Role role = new Role();
        utils.initModelMapperStrict().map(dto, role);
        role.setActive(true);

        role.getRights().clear();
        role.setRights(rights);

        roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );

        return toRoleDTO(role);
    }

    @Override
    @Transactional
    public void updateRole(RoleDTO dto) {
        Role role = roleRepository.findById(dto.getId()).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        List<Right> rights = rightRepository.findAllByNameIn(dto.getRights());

        role.setRoleName(dto.getRoleName());
        role.setActive(dto.getActive());

        role.getRights().clear();
        role.setRights(rights);

        roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDTO> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .map(this::toRoleDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDTO> searchByParams(SearchRoleParamsDTO dto, Pageable pageable) {
        QRole qRole = QRole.role;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (dto.getRoleName() != null) {
            booleanBuilder.and(qRole.roleName.containsIgnoreCase(dto.getRoleName()));
        }

        if (dto.getActive() != null) {
            booleanBuilder.and(qRole.active.eq(dto.getActive()));
        }

        return roleRepository.findAll(booleanBuilder, pageable)
                .map(this::toRoleDTO);
    }

    private RoleDTO toRoleDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        utils.initModelMapperStrict().map(role, dto);
        List<RightName> rights = role.getRights()
                .stream()
                .map(Right::getName)
                .toList();
        dto.setRights(rights);

        List<String> users = role.getUsers()
                .stream()
                .map(AzUser::getUsername)
                .toList();
        dto.setUsers(users);

        return dto;
    }
}
