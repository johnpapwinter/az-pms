package com.az.azpms.service;

import com.az.azpms.domain.dto.SearchTaskParamsDTO;
import com.az.azpms.domain.dto.TaskDTO;
import com.az.azpms.domain.entities.Task;
import com.az.azpms.domain.entities.TaskBid;
import com.az.azpms.domain.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    void createTask(TaskDTO dto);

    void updateTask(Long id, TaskDTO dto);

    TaskDTO getTaskById(Long id);

    List<TaskDTO> getAllTasksByProject(Long projectId);

    void changeTaskStatus(Long id, TaskStatus status);

    void assignTask(Task task, TaskBid bid);

    Page<TaskDTO> getAllTasksByContractor(Long contractorId, Pageable pageable);

    Page<TaskDTO> search(SearchTaskParamsDTO dto, Pageable pageable);

}
