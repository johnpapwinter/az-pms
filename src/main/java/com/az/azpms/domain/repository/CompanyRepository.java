package com.az.azpms.domain.repository;

import com.az.azpms.domain.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, QuerydslPredicateExecutor<Company> {

    Optional<Company> findCompanyByTitle(String title);

}
