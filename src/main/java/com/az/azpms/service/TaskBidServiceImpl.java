package com.az.azpms.service;


import com.az.azpms.domain.dto.TaskBidDTO;
import com.az.azpms.domain.entities.Contractor;
import com.az.azpms.domain.entities.Task;
import com.az.azpms.domain.entities.TaskBid;
import com.az.azpms.domain.enums.TaskBidStatus;
import com.az.azpms.domain.exceptions.AzAlreadyExistsException;
import com.az.azpms.domain.exceptions.AzErrorMessages;
import com.az.azpms.domain.exceptions.AzIllegalStatusChangeException;
import com.az.azpms.domain.exceptions.AzNotFoundException;
import com.az.azpms.domain.repository.ContractorRepository;
import com.az.azpms.domain.repository.TaskBidRepository;
import com.az.azpms.domain.repository.TaskRepository;
import com.az.azpms.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TaskBidServiceImpl implements TaskBidService {

    private final TaskBidRepository taskBidRepository;
    private final TaskRepository taskRepository;
    private final ContractorRepository contractorRepository;
    private final TaskService taskService;
    private final Utils utils;

    public TaskBidServiceImpl(TaskBidRepository taskBidRepository,
                              TaskRepository taskRepository,
                              ContractorRepository contractorRepository,
                              TaskService taskService,
                              Utils utils) {
        this.taskBidRepository = taskBidRepository;
        this.taskRepository = taskRepository;
        this.contractorRepository = contractorRepository;
        this.taskService = taskService;
        this.utils = utils;
    }

    @Override
    @Transactional
    public void createTaskBid(TaskBidDTO dto) {
        Task task = taskRepository.findById(dto.getTaskId()).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        Contractor contractor = contractorRepository.findById(dto.getContractorId()).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        taskBidRepository.findTaskBidByTaskAndContractor(task, contractor).ifPresent(
                taskBid -> {
                    throw new AzAlreadyExistsException(AzErrorMessages.CONTRACTOR_HAS_ALREADY_PLACED_A_BID.name());
                }
        );

        TaskBid taskBid = new TaskBid();
        utils.initModelMapperStrict().map(dto, taskBid);
        taskBid.setStatus(TaskBidStatus.UNRESOLVED);
        taskBid.setTask(task);
        taskBid.setContractor(contractor);

        taskBidRepository.save(taskBid);
    }

    @Override
    @Transactional
    public void updateTaskBid(Long id, TaskBidDTO dto) {
        TaskBid taskBid = taskBidRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        utils.initModelMapperStrict().map(dto, taskBid);

        taskBidRepository.save(taskBid);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskBidDTO getTaskBidById(Long id) {
        TaskBid taskBid = taskBidRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );

        return toTaskBidDTO(taskBid);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskBidDTO> getAllBidsByTask(Long taskId, Pageable pageable) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );

        return taskBidRepository.findAllByTask(task, pageable)
                .map(this::toTaskBidDTO);
    }

    @Override
    @Transactional
    public void changeTaskBidStatus(Long id, TaskBidStatus status) {
        TaskBid taskBid = taskBidRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        validateStatusChange(taskBid.getStatus());
        taskBid.setStatus(status);

        if (taskBid.getStatus().equals(TaskBidStatus.ACCEPTED)) {
            taskService.assignTask(taskBid.getTask(), taskBid);

            List<TaskBid> bids = taskBidRepository.findAllByTask(taskBid.getTask());
            bids
                    .stream()
                    .filter(bid -> !Objects.equals(bid.getId(), taskBid.getId()))
                    .forEach(bid -> {
                        bid.setStatus(TaskBidStatus.REJECTED);
                        taskBidRepository.save(bid);
                    });
        }

        taskBidRepository.save(taskBid);
    }


    private TaskBidDTO toTaskBidDTO(TaskBid taskBid) {
        TaskBidDTO dto = new TaskBidDTO();
        utils.initModelMapperStrict().map(taskBid, dto);
        dto.setTaskId(taskBid.getTask().getId());
        dto.setContractorId(taskBid.getContractor().getId());

        return dto;
    }

    private void validateStatusChange(TaskBidStatus currentStatus) {
        if (currentStatus.equals(TaskBidStatus.ACCEPTED) || currentStatus.equals(TaskBidStatus.REJECTED)) {
            throw new AzIllegalStatusChangeException(AzErrorMessages.TASK_BID_STATUS_ILLEGAL_CHANGE.name());
        }
    }

}
