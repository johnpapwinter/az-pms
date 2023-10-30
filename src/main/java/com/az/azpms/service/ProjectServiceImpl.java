package com.az.azpms.service;


import com.az.azpms.domain.dto.CompanyDTO;
import com.az.azpms.domain.dto.ProjectDTO;
import com.az.azpms.domain.dto.SearchProjectParamsDTO;
import com.az.azpms.domain.dto.TaskDTO;
import com.az.azpms.domain.entities.Company;
import com.az.azpms.domain.entities.Project;
import com.az.azpms.domain.entities.QProject;
import com.az.azpms.domain.enums.ProjectStatus;
import com.az.azpms.domain.exceptions.AzAlreadyExistsException;
import com.az.azpms.domain.exceptions.AzErrorMessages;
import com.az.azpms.domain.exceptions.AzIllegalStatusChangeException;
import com.az.azpms.domain.exceptions.AzNotFoundException;
import com.az.azpms.domain.repository.CompanyRepository;
import com.az.azpms.domain.repository.ProjectRepository;
import com.az.azpms.utils.Utils;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final Utils utils;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              CompanyRepository companyRepository,
                              Utils utils) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
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
        Company company = companyRepository.findById(dto.getCompany().getId()).orElseThrow(
                () -> new AzNotFoundException(AzErrorMessages.ENTITY_NOT_FOUND.name())
        );

        Project project = new Project();
        utils.initModelMapperStrict().map(dto, project);
        project.setStatus(ProjectStatus.OPEN);
        project.setCreationDate(LocalDateTime.now());
        project.setLastModificationDate(LocalDateTime.now());
        project.setCompany(company);
        company.getProjects().add(project);

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

        if (dto.getBudgetFrom() != null && dto.getBudgetTo() != null) {
            booleanBuilder.and(qProject.budget.between(dto.getBudgetFrom(), dto.getBudgetTo()));
        } else if (dto.getBudgetFrom() != null) {
            booleanBuilder.and(qProject.budget.goe(dto.getBudgetFrom()));
        } else if (dto.getBudgetTo() != null) {
            booleanBuilder.and(qProject.budget.loe(dto.getBudgetTo()));
        }

        if (dto.getCity() != null) {
            booleanBuilder.and(qProject.city.containsIgnoreCase(dto.getCity()));
        }

        if (dto.getCountry() != null) {
            booleanBuilder.and(qProject.country.containsIgnoreCase(dto.getCountry()));
        }

        if (dto.getStartDateFrom() != null && dto.getStartDateTo() != null) {
            booleanBuilder.and(qProject.startDate.between(dto.getStartDateFrom(), dto.getStartDateTo()));
        } else if (dto.getStartDateFrom() != null) {
            booleanBuilder.and(qProject.startDate.goe(dto.getStartDateFrom()));
        } else if (dto.getStartDateTo() != null) {
            booleanBuilder.and(qProject.startDate.loe(dto.getStartDateTo()));
        }

        if (dto.getEndDateFrom() != null && dto.getEndDateTo() != null) {
            booleanBuilder.and(qProject.endDate.between(dto.getEndDateFrom(), dto.getEndDateTo()));
        } else if (dto.getEndDateFrom() != null) {
            booleanBuilder.and(qProject.endDate.goe(dto.getEndDateFrom()));
        } else if (dto.getEndDateTo() != null) {
            booleanBuilder.and(qProject.endDate.loe(dto.getEndDateTo()));
        }

        if (dto.getDueDateFrom() != null && dto.getDueDateTo() != null) {
            booleanBuilder.and(qProject.dueDate.between(dto.getDueDateFrom(), dto.getDueDateTo()));
        } else if (dto.getDueDateFrom() != null) {
            booleanBuilder.and(qProject.dueDate.goe(dto.getDueDateFrom()));
        } else if (dto.getDueDateTo() != null) {
            booleanBuilder.and(qProject.dueDate.loe(dto.getDueDateTo()));
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
        Optional.ofNullable(project.getCompany()).ifPresent(temp -> {
            dto.setCompany(utils.initModelMapperStrict().map(project.getCompany(), CompanyDTO.class));
        });
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
