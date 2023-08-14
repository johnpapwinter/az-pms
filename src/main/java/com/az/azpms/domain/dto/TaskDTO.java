package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private LocalDate bidDueDate;
    private String description;
    private TaskStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate dueDate;
    private Double cost;
    private Long projectId;
}
