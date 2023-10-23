package com.az.azpms.domain.repository;

import com.az.azpms.domain.entities.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long>, QuerydslPredicateExecutor<Contractor> {

    Optional<Contractor> findContractorByName(String name);

}
