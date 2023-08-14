package com.az.azpms.domain.repository;

import com.az.azpms.domain.entities.Project;
import com.az.azpms.domain.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByProject(Project project);

}
