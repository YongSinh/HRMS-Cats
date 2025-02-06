package com.cats.informationmanagementservice.repository;

import com.cats.informationmanagementservice.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepo extends JpaRepository<Position, String> {
    List<Position> findAllByDepartment_DepId(Long department_depId);
}
