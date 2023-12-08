package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchTaskParamsDTO {

    private String title;
    private LocalDate bidDueDateFrom;
    private LocalDate bidDueDateTo;
    private TaskStatus status;
    private LocalDate startDateFrom;
    private LocalDate startDateTo;
    private LocalDate endDateFrom;
    private LocalDate endDateTo;
    private LocalDate dueDateFrom;
    private LocalDate dueDateTo;
    private Double costFrom;
    private Double costTo;

}
