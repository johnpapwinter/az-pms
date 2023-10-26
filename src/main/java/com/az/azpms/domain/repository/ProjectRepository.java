package com.az.azpms.domain.repository;

import com.az.azpms.domain.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, QuerydslPredicateExecutor<Project> {

    Optional<Project> findProjectByTitle(String title);

    Page<Project> findAllByCompanyId(Long companyId, Pageable pageable);

}
