package com.cats.informationmanagementservice.repository;

import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.SiblingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiblingDataRepo extends JpaRepository<SiblingData, Long> {
    List<SiblingData> findByEmployee(Employee employee);
}
