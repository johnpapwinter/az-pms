package com.az.azpms.domain.entities;

import com.az.azpms.domain.enums.RightName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rights")
@Getter
@Setter
public class Right implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RightName name;

    @ManyToMany(mappedBy = "rights")
    private List<Role> roles = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Right right = (Right) o;
        return Objects.equals(id, right.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
