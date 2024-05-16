package com.cats.informationmanagementservice.repository;

import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.FamilyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyDataRepo extends JpaRepository<FamilyData, Long> {

    List<FamilyData> findByEmployee(Employee employee);
}
