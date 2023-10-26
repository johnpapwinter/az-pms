package com.az.azpms.service;


import com.az.azpms.domain.dto.ProjectDTO;
import com.az.azpms.domain.dto.SearchProjectParamsDTO;
import com.az.azpms.domain.dto.TaskDTO;
import com.az.azpms.domain.entities.Project;
import com.az.azpms.domain.entities.QProject;
import com.az.azpms.domain.enums.ProjectStatus;
import com.az.azpms.domain.exceptions.AzAlreadyExistsException;
import com.az.azpms.domain.exceptions.AzErrorMessages;
import com.az.azpms.domain.exceptions.AzIllegalStatusChangeException;
import com.az.azpms.domain.exceptions.AzNotFoundException;
import com.az.azpms.domain.repository.ProjectRepository;
import com.az.azpms.utils.Utils;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final Utils utils;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              Utils utils) {
        this.projectRepository = projectRepository;
        this.utils = utils;
    }

    @Override
    @Transactional
    public void createProject(ProjectDTO dto) {
        projectRepository.findProjectByTitle(dto.getTitle()).ifPresent(
                project -> {
                    throw new AzAlreadyExistsException(AzErrorMessages.ENTITY_ALREADY_EXISTS.name());
                }
        );

        Project project = new Project();
        utils.initModelMapperStrict().map(dto, project);
        project.setStatus(ProjectStatus.OPEN);
        project.setCreationDate(LocalDateTime.now());
        project.setLastModificationDate(LocalDateTime.now());

        projectRepository.save(project);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );

        return toProjectDTO(project);
    }

    @Override
    public Page<ProjectDTO> getProjectsOfCompany(Long companyId, Pageable pageable) {
        return projectRepository.findAllByCompanyId(companyId, pageable)
                .map(this::toProjectDTO);
    }

    @Override
    @Transactional
    public void updateProject(Long id, ProjectDTO dto) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );

        if (!project.getStatus().equals(dto.getStatus())) {
            validateProjectStatusChange(project.getStatus(), dto.getStatus());
        }

        utils.initModelMapperStrict().map(dto, project);
        project.setLastModificationDate(LocalDateTime.now());

        projectRepository.save(project);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectDTO> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable)
                .map(this::toProjectDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectDTO> searchByParameters(SearchProjectParamsDTO dto, Pageable pageable) {
        QProject qProject = QProject.project;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (dto.getTitle() != null) {
            booleanBuilder.and(qProject.title.containsIgnoreCase(dto.getTitle()));
        }

        if (dto.getStatus() != null) {
            booleanBuilder.and(qProject.status.eq(dto.getStatus()));
        }

        if (dto.getBudget() != null) {
            booleanBuilder.and(qProject.budget.eq(dto.getBudget()));
        }

        if (dto.getCity() != null) {
            booleanBuilder.and(qProject.city.containsIgnoreCase(dto.getCity()));
        }

        if (dto.getCountry() != null) {
            booleanBuilder.and(qProject.country.containsIgnoreCase(dto.getCountry()));
        }

        if (dto.getStartDate() != null) {
            booleanBuilder.and(qProject.startDate.eq(dto.getStartDate()));
        }

        if (dto.getEndDate() != null) {
            booleanBuilder.and(qProject.endDate.eq(dto.getEndDate()));
        }

        if (dto.getDueDate() != null) {
            booleanBuilder.and(qProject.dueDate.eq(dto.getDueDate()));
        }

        return projectRepository.findAll(booleanBuilder, pageable)
                .map(this::toProjectDTO);
    }

    @Override
    @Transactional
    public void changeProjectStatus(Long id, ProjectStatus status) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );
        validateProjectStatusChange(project.getStatus(), status);
        project.setStatus(status);

        projectRepository.save(project);
    }

    private ProjectDTO toProjectDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        utils.initModelMapperStrict().map(project, dto);
        List<TaskDTO> taskDTOList = project.getTasks()
                .stream().map(task -> utils.initModelMapperStrict().map(task, TaskDTO.class))
                .toList();
        dto.setTasks(taskDTOList);

        return dto;
    }

    private void validateProjectStatusChange(ProjectStatus currentStatus, ProjectStatus nextStatus) {
        if (currentStatus.equals(ProjectStatus.DELETED) || currentStatus.equals(ProjectStatus.CLOSED)) {
            throw new AzIllegalStatusChangeException(AzErrorMessages.PROJECT_STATUS_IS_FINAL.name());
        }

        if (currentStatus.equals(ProjectStatus.OPEN) &&
                !(nextStatus.equals(ProjectStatus.PENDING) || nextStatus.equals(ProjectStatus.DELETED))) {
            throw new AzIllegalStatusChangeException(AzErrorMessages.PROJECT_STATUS_ILLEGAL_CHANGE.name());
        }

        if (currentStatus.equals(ProjectStatus.PENDING) &&
                !(nextStatus.equals(ProjectStatus.COMPLETED) || nextStatus.equals(ProjectStatus.DELETED))) {
            throw new AzIllegalStatusChangeException(AzErrorMessages.PROJECT_STATUS_ILLEGAL_CHANGE.name());
        }

        if (currentStatus.equals(ProjectStatus.COMPLETED) &&
                !(nextStatus.equals(ProjectStatus.CLOSED) || nextStatus.equals(ProjectStatus.DELETED))) {
            throw new AzIllegalStatusChangeException(AzErrorMessages.PROJECT_STATUS_ILLEGAL_CHANGE.name());
        }

    }

}
