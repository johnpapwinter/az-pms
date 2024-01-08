package com.az.azpms.domain.repository;

import com.az.azpms.domain.entities.Right;
import com.az.azpms.domain.enums.RightName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RightRepository extends JpaRepository<Right, Long> {

    List<Right> findAllByNameIn(List<RightName> names);

}
