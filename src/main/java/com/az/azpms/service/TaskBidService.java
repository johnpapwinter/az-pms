package com.az.azpms.service;

import com.az.azpms.domain.dto.SearchBidParamsDTO;
import com.az.azpms.domain.dto.TaskBidDTO;
import com.az.azpms.domain.enums.TaskBidStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskBidService {

    void createTaskBid(TaskBidDTO dto);

    void updateTaskBid(Long id, TaskBidDTO dto);

    TaskBidDTO getTaskBidById(Long id);

    Page<TaskBidDTO> getAllBidsByTask(Long taskId, Pageable pageable);

    void changeTaskBidStatus(Long id, TaskBidStatus status);

    Page<TaskBidDTO> getAllBidsByContractor(Long contractorId, Pageable pageable);

    Page<TaskBidDTO> search(SearchBidParamsDTO dto, Pageable pageable);

}
