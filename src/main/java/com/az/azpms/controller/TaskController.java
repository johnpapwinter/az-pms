package com.az.azpms.controller;

import com.az.azpms.domain.dto.SearchTaskParamsDTO;
import com.az.azpms.domain.dto.TaskDTO;
import com.az.azpms.domain.enums.TaskStatus;
import com.az.azpms.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody TaskDTO dto) {
        taskService.createTask(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable("id") Long id) {
        TaskDTO response = taskService.getTaskById(id);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable("id") Long id, @RequestBody TaskDTO dto) {
        taskService.updateTask(id, dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-project/{id}")
    public ResponseEntity<List<TaskDTO>> getTasksByProject(@PathVariable("id") Long projectId) {
        List<TaskDTO> response = taskService.getAllTasksByProject(projectId);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/change-status/{id}/{status}")
    public ResponseEntity<Void> changeTaskStatus(@PathVariable("id") Long id,
                                                 @PathVariable("status") TaskStatus status) {
        taskService.changeTaskStatus(id, status);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-contractor/{id}")
    public ResponseEntity<Page<TaskDTO>> getTasksByContractor(@PathVariable("id") Long contractorId,
                                                              Pageable pageable) {
        Page<TaskDTO> response = taskService.getAllTasksByContractor(contractorId, pageable);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<TaskDTO>> search(@RequestBody SearchTaskParamsDTO dto,
                                                Pageable pageable) {
        Page<TaskDTO> response = taskService.search(dto, pageable);

        return ResponseEntity.ok().body(response);
    }

}
