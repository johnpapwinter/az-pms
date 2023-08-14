package com.az.azpms.service;

import com.az.azpms.domain.dto.TaskBidDTO;
import com.az.azpms.domain.enums.TaskBidStatus;

import java.util.List;

public interface TaskBidService {

    void createTaskBid(TaskBidDTO dto);

    void updateTaskBid(Long id, TaskBidDTO dto);

    TaskBidDTO getTaskBidById(Long id);

    List<TaskBidDTO> getAllBidsByTask(Long taskId);

    void changeTaskBidStatus(Long id, TaskBidStatus status);

}
