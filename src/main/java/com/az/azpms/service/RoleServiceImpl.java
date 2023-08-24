package com.az.azpms.service;

import com.az.azpms.domain.dto.RoleDTO;
import com.az.azpms.domain.entities.AzUser;
import com.az.azpms.domain.entities.Role;
import com.az.azpms.domain.exceptions.AzAlreadyExistsException;
import com.az.azpms.domain.exceptions.AzErrorMessages;
import com.az.azpms.domain.exceptions.AzNotFoundException;
import com.az.azpms.domain.repository.RoleRepository;
import com.az.azpms.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final Utils utils;

    public RoleServiceImpl(RoleRepository roleRepository, Utils utils) {
        this.roleRepository = roleRepository;
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

        Role role = new Role();
        utils.initModelMapperStrict().map(dto, role);
        role.setActive(true);

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
        role.setRoleName(dto.getRoleName());
        role.setActive(dto.getActive());

        roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDTO> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .map(this::toRoleDTO);
    }

    private RoleDTO toRoleDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        utils.initModelMapperStrict().map(role, dto);
        List<String> rights = role.getRights()
                .stream()
                .map(right -> String.valueOf(right.getName()))
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
