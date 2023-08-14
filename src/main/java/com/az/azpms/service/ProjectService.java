package com.az.azpms.service;

import com.az.azpms.domain.dto.ProjectDTO;
import com.az.azpms.domain.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    void createProject(ProjectDTO dto);

    ProjectDTO getProjectById(Long id);

    void updateProject(Long id, ProjectDTO dto);

    Page<ProjectDTO> getAllProjects(Pageable pageable);

    void changeProjectStatus(Long id, ProjectStatus status);

}
