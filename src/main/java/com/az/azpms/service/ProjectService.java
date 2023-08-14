package com.az.azpms.service;

import com.az.azpms.domain.dto.ProjectDTO;
import com.az.azpms.domain.enums.ProjectStatus;
import org.springframework.data.domain.Page;

public interface ProjectService {

    void createProject(ProjectDTO dto);

    ProjectDTO getProjectById(Long id);

    void updateProject(Long id, ProjectDTO dto);

    Page<ProjectDTO> getAllProjects(int page, int size);

    void changeProjectStatus(Long id, ProjectStatus status);

}
