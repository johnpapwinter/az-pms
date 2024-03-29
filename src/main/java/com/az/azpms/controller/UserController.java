package com.az.azpms.controller;

import com.az.azpms.domain.dto.AzUserDTO;
import com.az.azpms.domain.dto.RegistrationDTO;
import com.az.azpms.domain.dto.SearchAzUserParamsDTO;
import com.az.azpms.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody AzUserDTO dto) {
        userService.updateUser(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<AzUserDTO> getUserById(@PathVariable("id") Long id) {
        AzUserDTO response = userService.findUserById(id);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/toggle-status")
    public ResponseEntity<Void> toggleUserStatus(@RequestBody AzUserDTO dto) {
        userService.toggleUserStatus(dto);


        return ResponseEntity.ok().build();
    }

    @PostMapping("/assign-roles/{id}")
    public ResponseEntity<Void> assignRolesToUser(@PathVariable("id") Long userId,
                                                  @RequestBody List<Long> roleIds) {
        userService.assignRolesToUser(userId, roleIds);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public ResponseEntity<Page<AzUserDTO>> searchUsersByParams(@RequestBody SearchAzUserParamsDTO dto,
                                                               Pageable pageable) {
        Page<AzUserDTO> response = userService.searchUsers(dto, pageable);

        return ResponseEntity.ok().body(response);
    }

}
