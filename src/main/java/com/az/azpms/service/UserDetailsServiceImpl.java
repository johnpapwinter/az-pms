package com.az.azpms.service;

import com.az.azpms.domain.entities.AzUser;
import com.az.azpms.domain.entities.AzUserPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AzUserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        AzUser user = userService.findUserByUsername(username);

        return new AzUserPrincipal(user.getUsername(), user.getPassword());
    }
}
