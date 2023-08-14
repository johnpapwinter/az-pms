package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.ProjectStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDTO {

    private Long id;
    private String title;
    private ProjectStatus status;
    private String description;
    private Double budget;
    private String city;
    private String country;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate dueDate;

}
