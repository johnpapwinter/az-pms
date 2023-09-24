package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {

    private Long id;
    @NotBlank
    private String title;
    @NotNull
    private LocalDate bidDueDate;
    private String description;
    private TaskStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    @NotNull
    private LocalDate dueDate;
    @PositiveOrZero
    private Double cost;
    @NotNull
    private Long projectId;

}
