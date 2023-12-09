package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.TaskBidStatus;
import lombok.Data;

@Data
public class SearchBidParamsDTO {

    private Double offerFrom;
    private Double offerTo;
    private TaskBidStatus status;
    private Long taskId;
    private Long contractorId;

}
