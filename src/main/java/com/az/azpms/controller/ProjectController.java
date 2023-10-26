package com.az.azpms.controller;

import com.az.azpms.domain.dto.ProjectDTO;
import com.az.azpms.domain.dto.SearchProjectParamsDTO;
import com.az.azpms.domain.enums.ProjectStatus;
import com.az.azpms.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<Void> createProject(@RequestBody ProjectDTO dto) {
        projectService.createProject(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable("id") Long id) {
        ProjectDTO response = projectService.getProjectById(id);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get-by-company/{id}")
    public ResponseEntity<Page<ProjectDTO>> getProjectsByCompany(@PathVariable("id") Long companyId,
                                                                 Pageable pageable) {
        Page<ProjectDTO> response = projectService.getProjectsOfCompany(companyId, pageable);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ProjectDTO>> searchByParameters(@RequestBody SearchProjectParamsDTO dto,
                                                               Pageable pageable) {
        Page<ProjectDTO> response = projectService.searchByParameters(dto, pageable);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateProject(@PathVariable("id") Long id, @RequestBody ProjectDTO dto) {
        projectService.updateProject(id, dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> getAllProjects(Pageable pageable) {
        Page<ProjectDTO> response = projectService.getAllProjects(pageable);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/change-status/{id}/{status}")
    public ResponseEntity<Void> changeProjectStatus(@PathVariable("id") Long id,
                                                    @PathVariable("status") ProjectStatus status) {
        projectService.changeProjectStatus(id, status);

        return ResponseEntity.ok().build();
    }

}
