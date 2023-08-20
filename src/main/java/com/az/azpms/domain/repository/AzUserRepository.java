package com.az.azpms.domain.repository;

import com.az.azpms.domain.entities.AzUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AzUserRepository extends JpaRepository<AzUser, Long> {

    Optional<AzUser> findAzUserByUsername(String username);

}
