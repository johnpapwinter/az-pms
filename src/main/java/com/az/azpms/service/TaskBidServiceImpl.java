package com.az.azpms.service;


import com.az.azpms.domain.dto.TaskBidDTO;
import com.az.azpms.domain.enums.TaskBidStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskBidServiceImpl implements TaskBidService {

    @Override
    public void createTaskBid(TaskBidDTO dto) {

    }

    @Override
    public void updateTaskBid(Long id, TaskBidDTO dto) {

    }

    @Override
    public TaskBidDTO getTaskBidById(Long id) {
        return null;
    }

    @Override
    public List<TaskBidDTO> getAllBidsByTask(Long taskId) {
        return null;
    }

    @Override
    public void changeTaskBidStatus(Long id, TaskBidStatus status) {

    }
}
