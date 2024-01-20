package com.az.azpms.initializer;

import com.az.azpms.Application;
import com.az.azpms.domain.entities.AzUser;
import com.az.azpms.domain.entities.Right;
import com.az.azpms.domain.entities.Role;
import com.az.azpms.domain.enums.AzUserStatus;
import com.az.azpms.domain.enums.RightName;
import com.az.azpms.domain.repository.AzUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Component
public class UserInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Value("${app.sys.admin.email}")
    private String sysAdminEmail;

    @Value("${app.sys.admin.password}")
    private String sysAdminPassword;

    private final AzUserRepository repository;
    private final PasswordEncoder encoder;

    public UserInitializer(AzUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        AzUser systemAdmin = repository.findAzUserByEmail(sysAdminEmail).orElse(new AzUser());
        if (systemAdmin.getEmail() == null) {
            systemAdmin.setUsername("azadmin");
            systemAdmin.setEmail(sysAdminEmail);
            systemAdmin.setPassword(encoder.encode(sysAdminPassword));
            systemAdmin.setStatus(AzUserStatus.ACTIVE);
            systemAdmin.setFirstname("SYSTEM");
            systemAdmin.setLastname("ADMIN");

            Right right = new Right();
            right.setName(RightName.ADMIN);

            Role role = new Role();
            role.setRoleName("SYSTEM_ADMIN");
            role.getRights().add(right);

            systemAdmin.getRoles().add(role);

            log.info("SAVED SYSTEM ADMIN USER");
            log.info(systemAdmin.toString());
            repository.save(systemAdmin);
        } else {
            log.info("SYSTEM ADMIN ALREADY EXISTS");
        }

    }
}
