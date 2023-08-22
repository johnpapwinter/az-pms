package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.TaskBidStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TaskBidDTO {

    private Long id;
    @NotNull
    @Positive
    private Double offer;
    private TaskBidStatus status;
    @NotNull
    private Long taskId;
    @NotNull
    private Long contractorId;

}
