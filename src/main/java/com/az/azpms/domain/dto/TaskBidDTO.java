package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.TaskBidStatus;
import lombok.Data;

@Data
public class TaskBidDTO {

    private Long id;
    private Double offer;
    private TaskBidStatus status;
    private Long taskId;
    private Long contractorId;

}
