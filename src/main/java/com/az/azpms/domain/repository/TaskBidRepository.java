package com.az.azpms.domain.repository;

import com.az.azpms.domain.entities.Task;
import com.az.azpms.domain.entities.TaskBid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskBidRepository extends JpaRepository<TaskBid, Long> {

    Page<TaskBidRepository> findAllByTask(Task task);

}
