package com.cats.informationmanagementservice.repository;

import com.cats.informationmanagementservice.model.Education;
import com.cats.informationmanagementservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepo extends JpaRepository<Education, Long> {

    List<Education> findByEmployee(Employee employee);
}
