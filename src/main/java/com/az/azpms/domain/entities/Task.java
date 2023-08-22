package com.az.azpms.domain.entities;

import com.az.azpms.domain.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "bid_due_date", nullable = false)
    private LocalDate bidDueDate;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "last_modification_date")
    private LocalDateTime lastModificationDate;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "contractor_id", referencedColumnName = "id")
    private Contractor contractor;

    @OneToMany(mappedBy = "task")
    private List<TaskBid> taskBids = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
