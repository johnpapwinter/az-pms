package com.az.azpms.controller;

import com.az.azpms.domain.dto.RoleDTO;
import com.az.azpms.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Page<RoleDTO>> getAllRoles(Pageable pageable) {
        Page<RoleDTO> response = roleService.getAllRoles(pageable);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody @Valid RoleDTO dto) {
        roleService.createRole(dto);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateRole(@RequestBody @Valid RoleDTO dto) {
        roleService.updateRole(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable("id") Long id) {
        RoleDTO response = roleService.getRoleById(id);

        return ResponseEntity.ok().body(response);
    }
}
