package com.az.azpms.domain.repository;

import com.az.azpms.domain.entities.Right;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RightRepository extends JpaRepository<Right, Long> {

}
