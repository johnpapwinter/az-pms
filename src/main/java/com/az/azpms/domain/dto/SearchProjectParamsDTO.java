package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.ProjectStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchProjectParamsDTO {

    private String title;
    private ProjectStatus status;
    private Double budget;
    private String city;
    private String country;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate dueDate;

}
