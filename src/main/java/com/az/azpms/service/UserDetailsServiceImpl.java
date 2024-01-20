package com.az.azpms.service;

import com.az.azpms.domain.entities.AzUser;
import com.az.azpms.domain.entities.AzUserPrincipal;
import com.az.azpms.domain.entities.Right;
import com.az.azpms.domain.enums.RightName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AzUserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        AzUser user = userService.findUserByUsername(username);
        Set<RightName> rightNames = user.getRoles()
                .stream()
                .flatMap(role -> role.getRights().stream())
                .map(Right::getName)
                .collect(Collectors.toSet());

        List<GrantedAuthority> authorities = rightNames
                .stream()
                .map(rightName -> new SimpleGrantedAuthority(rightName.name()))
                .collect(Collectors.toList());

        return new AzUserPrincipal(user.getUsername(), user.getPassword(), user.getId(), authorities);
    }
}
