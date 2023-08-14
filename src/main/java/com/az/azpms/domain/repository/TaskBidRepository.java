package com.az.azpms.domain.repository;

import com.az.azpms.domain.entities.Task;
import com.az.azpms.domain.entities.TaskBid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskBidRepository extends JpaRepository<TaskBid, Long> {

    List<TaskBid> findAllByTask(Task task);

    Page<TaskBid> findAllByTask(Task task, Pageable pageable);

}
