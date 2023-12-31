package com.az.azpms.domain.repository;

import com.az.azpms.domain.entities.Project;
import com.az.azpms.domain.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, QuerydslPredicateExecutor<Task> {

    List<Task> findAllByProject(Project project);

    Page<Task> findAllByProject(Project project, Pageable pageable);

    Optional<Task> findTaskByTitleAndProject(String title, Project project);

    Page<Task> findAllByContractorId(Long contractorId, Pageable pageable);

}
