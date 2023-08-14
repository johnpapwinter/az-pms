package com.az.azpms.service;


import com.az.azpms.domain.dto.ProjectDTO;
import com.az.azpms.domain.enums.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {


    @Override
    public void createProject(ProjectDTO dto) {

    }

    @Override
    public ProjectDTO getProjectById(Long id) {
        return null;
    }

    @Override
    public void updateProject(Long id, ProjectDTO dto) {

    }

    @Override
    public Page<ProjectDTO> getAllProjects(int page, int size) {
        return null;
    }

    @Override
    public void changeProjectStatus(Long id, ProjectStatus status) {

    }
}
