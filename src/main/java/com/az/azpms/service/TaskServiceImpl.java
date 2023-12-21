package com.az.azpms.service;


import com.az.azpms.domain.dto.SearchTaskParamsDTO;
import com.az.azpms.domain.dto.TaskDTO;
import com.az.azpms.domain.entities.Project;
import com.az.azpms.domain.entities.QTask;
import com.az.azpms.domain.entities.Task;
import com.az.azpms.domain.entities.TaskBid;
import com.az.azpms.domain.enums.TaskStatus;
import com.az.azpms.domain.exceptions.AzAlreadyExistsException;
import com.az.azpms.domain.exceptions.AzErrorMessages;
import com.az.azpms.domain.exceptions.AzIllegalStatusChangeException;
import com.az.azpms.domain.exceptions.AzNotFoundException;
import com.az.azpms.domain.repository.ProjectRepository;
import com.az.azpms.domain.repository.TaskRepository;
import com.az.azpms.utils.Utils;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final Utils utils;

    public TaskServiceImpl(TaskRepository taskRepository,
                           ProjectRepository projectRepository,
                           Utils utils) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.utils = utils;
    }

    @Override
    @Transactional
    public void createTask(TaskDTO dto) {
        Project project = projectRepository.findById(dto.getProjectId()).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        taskRepository.findTaskByTitleAndProject(dto.getTitle(), project).ifPresent(
                task -> {
                    throw new AzAlreadyExistsException(AzErrorMessages.ENTITY_ALREADY_EXISTS.name());
                }
        );

        Task task = new Task();
        utils.initModelMapperStrict().map(dto, task);
        task.setStatus(TaskStatus.OPEN);
        task.setProject(project);
        task.setCreationDate(LocalDateTime.now());
        task.setLastModificationDate(LocalDateTime.now());


        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void updateTask(Long id, TaskDTO dto) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        utils.initModelMapperStrict().map(dto, task);
        task.setLastModificationDate(LocalDateTime.now());

        taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );

        return toTaskDTO(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getAllTasksByProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );

        return taskRepository.findAllByProject(project)
                .stream()
                .map(this::toTaskDTO)
                .toList();
    }

    @Override
    @Transactional
    public void changeTaskStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        validateStatusChange(task.getStatus(), status);
        task.setStatus(status);

        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void assignTask(Task task, TaskBid bid) {
        validateStatusChange(task.getStatus(), TaskStatus.ASSIGNED);

        task.setCost(bid.getOffer());
        task.setContractor(bid.getContractor());
        task.setStatus(TaskStatus.ASSIGNED);

        taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskDTO> getAllTasksByContractor(Long contractorId, Pageable pageable) {
        return taskRepository.findAllByContractorId(contractorId, pageable)
                .map(this::toTaskDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskDTO> search(SearchTaskParamsDTO dto, Pageable pageable) {
        QTask qTask = QTask.task;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (dto.getTitle() != null) {
            booleanBuilder.and(qTask.title.containsIgnoreCase(dto.getTitle()));
        }

        if (dto.getBidDueDateFrom() != null && dto.getBidDueDateTo() != null) {
            booleanBuilder.and(qTask.bidDueDate.between(dto.getBidDueDateFrom(), dto.getBidDueDateTo()));
        } else if (dto.getBidDueDateFrom() != null) {
            booleanBuilder.and(qTask.bidDueDate.goe(dto.getBidDueDateFrom()));
        } else if (dto.getBidDueDateTo() != null) {
            booleanBuilder.and(qTask.bidDueDate.loe(dto.getBidDueDateTo()));
        }

        if (dto.getStatus() != null) {
            booleanBuilder.and(qTask.status.eq(dto.getStatus()));
        }

        if (dto.getStartDateFrom() != null && dto.getStartDateTo() != null) {
            booleanBuilder.and(qTask.startDate.between(dto.getStartDateFrom(), dto.getStartDateTo()));
        } else if (dto.getStartDateFrom() != null) {
            booleanBuilder.and(qTask.startDate.goe(dto.getStartDateFrom()));
        } else if (dto.getStartDateTo() != null) {
            booleanBuilder.and(qTask.startDate.loe(dto.getStartDateTo()));
        }

        if (dto.getDueDateFrom() != null && dto.getDueDateTo() != null) {
            booleanBuilder.and(qTask.dueDate.between(dto.getDueDateFrom(), dto.getDueDateTo()));
        } else if (dto.getDueDateFrom() != null) {
            booleanBuilder.and(qTask.dueDate.goe(dto.getDueDateFrom()));
        } else if (dto.getDueDateTo() != null) {
            booleanBuilder.and(qTask.dueDate.loe(dto.getDueDateTo()));
        }

        if (dto.getCostFrom() != null && dto.getCostTo() != null) {
            booleanBuilder.and(qTask.cost.between(dto.getCostFrom(), dto.getCostTo()));
        } else if (dto.getCostFrom() != null) {
            booleanBuilder.and(qTask.cost.goe(dto.getCostFrom()));
        } else if (dto.getCostTo() != null) {
            booleanBuilder.and(qTask.cost.loe(dto.getCostTo()));
        }


        return taskRepository.findAll(booleanBuilder, pageable)
                .map(this::toTaskDTO);
    }


    private TaskDTO toTaskDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        utils.initModelMapperStrict().map(task, dto);
        dto.setProjectId(task.getProject().getId());

        return dto;
    }

    private void validateStatusChange(TaskStatus currentStatus, TaskStatus nextStatus) {
        if (currentStatus.equals(TaskStatus.DELETED) || currentStatus.equals(TaskStatus.CLOSED)) {
            throw new AzIllegalStatusChangeException(AzErrorMessages.TASK_STATUS_ILLEGAL_CHANGE.name());
        }

        if (currentStatus.equals(TaskStatus.OPEN) &&
                !(nextStatus.equals(TaskStatus.ASSIGNED) || nextStatus.equals(TaskStatus.DELETED))) {
            throw new AzIllegalStatusChangeException(AzErrorMessages.TASK_STATUS_ILLEGAL_CHANGE.name());
        }

        if (currentStatus.equals(TaskStatus.ASSIGNED) &&
                !(nextStatus.equals(TaskStatus.ONGOING) || nextStatus.equals(TaskStatus.DELETED))) {
            throw new AzIllegalStatusChangeException(AzErrorMessages.TASK_STATUS_ILLEGAL_CHANGE.name());
        }

        if (currentStatus.equals(TaskStatus.ONGOING) &&
                !(nextStatus.equals(TaskStatus.COMPLETED) || nextStatus.equals(TaskStatus.DELETED))) {
            throw new AzIllegalStatusChangeException(AzErrorMessages.TASK_STATUS_ILLEGAL_CHANGE.name());
        }

        if (currentStatus.equals(TaskStatus.COMPLETED) &&
                !(nextStatus.equals(TaskStatus.CLOSED) || nextStatus.equals(TaskStatus.DELETED))) {
            throw new AzIllegalStatusChangeException(AzErrorMessages.TASK_STATUS_ILLEGAL_CHANGE.name());
        }
    }

}
