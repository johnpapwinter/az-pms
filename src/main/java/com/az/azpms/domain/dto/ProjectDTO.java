package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.ProjectStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDTO {

    private Long id;
    @NotNull
    private String title;
    private ProjectStatus status;
    private String description;
    @NotNull
    @Positive
    private Double budget;
    @NotNull
    private String city;
    @NotNull
    private String country;
    private LocalDate startDate;
    private LocalDate endDate;
    @NotNull
    private LocalDate dueDate;

}
