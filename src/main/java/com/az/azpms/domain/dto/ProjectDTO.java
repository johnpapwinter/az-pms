package com.az.azpms.domain.dto;

import com.az.azpms.domain.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectDTO {

    private Long id;
    @NotBlank
    private String title;
    private ProjectStatus status;
    private String description;
    @NotNull
    @Positive
    private Double budget;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    private LocalDate startDate;
    private LocalDate endDate;
    @NotNull
    private LocalDate dueDate;
    private CompanyDTO company;
    private List<TaskDTO> tasks;

}
