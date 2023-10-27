package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.ProjectStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchProjectParamsDTO {

    private String title;
    private ProjectStatus status;
    private Double budgetFrom;
    private Double budgetTo;
    private String city;
    private String country;
    private LocalDate startDateFrom;
    private LocalDate startDateTo;
    private LocalDate endDateFrom;
    private LocalDate endDateTo;
    private LocalDate dueDateFrom;
    private LocalDate dueDateTo;

}
