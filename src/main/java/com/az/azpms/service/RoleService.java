package com.az.azpms.service;

import com.az.azpms.domain.dto.RoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    void createRole(RoleDTO dto);

    RoleDTO getRoleById(Long id);

    void updateRole(RoleDTO dto);

    Page<RoleDTO> getAllRoles(Pageable pageable);

}
