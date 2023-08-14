package com.az.azpms.service;


import com.az.azpms.domain.dto.TaskDTO;
import com.az.azpms.domain.entities.Task;
import com.az.azpms.domain.entities.TaskBid;
import com.az.azpms.domain.enums.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {


    @Override
    public void createTask(TaskDTO dto) {

    }

    @Override
    public void updateTask(Long id, TaskDTO dto) {

    }

    @Override
    public TaskDTO getTaskById(Long id) {
        return null;
    }

    @Override
    public List<TaskDTO> getAllTasksByProject(Long projectId) {
        return null;
    }

    @Override
    public void changeTaskStatus(Long id, TaskStatus status) {

    }

    @Override
    public void assignTask(Task task, TaskBid bid) {

    }
}
