package com.az.azpms.domain.repository;

import com.az.azpms.domain.entities.AzUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AzUserRepository extends JpaRepository<AzUser, Long> {

}
