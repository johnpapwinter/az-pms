package com.az.azpms.domain.repository;

import com.az.azpms.domain.entities.Project;
import com.az.azpms.domain.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByProject(Project project);

    Page<Task> findAllByProject(Project project, Pageable pageable);

}
