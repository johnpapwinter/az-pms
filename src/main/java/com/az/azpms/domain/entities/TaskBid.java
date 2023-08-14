package com.az.azpms.domain.entities;

import com.az.azpms.domain.enums.TaskBidStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "task_bids")
@Getter
@Setter
public class TaskBid implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "offer", nullable = false)
    private Double offer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskBidStatus status;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "contractor_id", referencedColumnName = "id")
    private Contractor contractor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskBid taskBid = (TaskBid) o;
        return Objects.equals(id, taskBid.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
