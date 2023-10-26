package com.az.azpms.service;

import com.az.azpms.domain.dto.ProjectDTO;
import com.az.azpms.domain.dto.SearchProjectParamsDTO;
import com.az.azpms.domain.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    void createProject(ProjectDTO dto);

    ProjectDTO getProjectById(Long id);

    Page<ProjectDTO> getProjectsOfCompany(Long companyId, Pageable pageable);

    void updateProject(Long id, ProjectDTO dto);

    Page<ProjectDTO> getAllProjects(Pageable pageable);

    Page<ProjectDTO> searchByParameters(SearchProjectParamsDTO dto, Pageable pageable);

    void changeProjectStatus(Long id, ProjectStatus status);

}
