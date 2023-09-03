package com.az.azpms.controller;

import com.az.azpms.domain.dto.JwtResponseDTO;
import com.az.azpms.domain.dto.LoginDTO;
import com.az.azpms.domain.dto.RegistrationDTO;
import com.az.azpms.domain.entities.AzUserPrincipal;
import com.az.azpms.domain.enums.RightName;
import com.az.azpms.security.JwtUtils;
import com.az.azpms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService,
                          PasswordEncoder passwordEncoder,
                          JwtUtils jwtUtils,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid RegistrationDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        userService.createUser(dto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwt(authentication);
        AzUserPrincipal userPrincipal = (AzUserPrincipal) authentication.getPrincipal();

        JwtResponseDTO responseDTO = new JwtResponseDTO();
        responseDTO.setToken(jwt);
        responseDTO.setUsername(userPrincipal.getUsername());
        responseDTO.setRights(userPrincipal.getAuthorities().stream()
                .map(grantedAuthority -> RightName.valueOf(grantedAuthority.getAuthority())).toList());

        return ResponseEntity.ok().body(responseDTO);
    }
}
