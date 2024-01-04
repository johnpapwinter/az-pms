package com.az.azpms.domain.repository;

import com.az.azpms.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, QuerydslPredicateExecutor<Role> {

    Optional<Role> findRoleByRoleName(String name);

    List<Role> findAllByIdIn(List<Long> roleIds);

}
