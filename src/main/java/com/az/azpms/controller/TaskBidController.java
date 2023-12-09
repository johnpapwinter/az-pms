package com.az.azpms.controller;

import com.az.azpms.domain.dto.SearchBidParamsDTO;
import com.az.azpms.domain.dto.TaskBidDTO;
import com.az.azpms.domain.enums.TaskBidStatus;
import com.az.azpms.service.TaskBidService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bids")
public class TaskBidController {

    private final TaskBidService taskBidService;

    public TaskBidController(TaskBidService taskBidService) {
        this.taskBidService = taskBidService;
    }

    @PostMapping
    public ResponseEntity<Void> createBid(@RequestBody TaskBidDTO dto) {
        taskBidService.createTaskBid(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<TaskBidDTO> getBidById(@PathVariable("id") Long id) {
        TaskBidDTO response = taskBidService.getTaskBidById(id);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateBid(@PathVariable("id") Long id, @RequestBody TaskBidDTO dto) {
        taskBidService.updateTaskBid(id, dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-task/{id}")
    public ResponseEntity<Page<TaskBidDTO>> getBidsByTask(@PathVariable("id") Long taskId, Pageable pageable) {
        Page<TaskBidDTO> response = taskBidService.getAllBidsByTask(taskId, pageable);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/change-status/{id}/{status}")
    public ResponseEntity<Void> changeTaskStatus(@PathVariable("id") Long id,
                                                 @PathVariable("status") TaskBidStatus status) {
        taskBidService.changeTaskBidStatus(id, status);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-by-contractor/{id}")
    public ResponseEntity<Page<TaskBidDTO>> getBidsByContractor(@PathVariable("id") Long contractorId,
                                                                Pageable pageable) {
        Page<TaskBidDTO> response = taskBidService.getAllBidsByContractor(contractorId, pageable);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<TaskBidDTO>> search(@RequestBody SearchBidParamsDTO dto,
                                                   Pageable pageable) {
        Page<TaskBidDTO> response = taskBidService.search(dto, pageable);

        return ResponseEntity.ok().body(response);
    }

}
