package com.cats.informationmanagementservice.repository;

import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.JobHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobHistoryRepo extends JpaRepository<JobHistory,Long> {
    List<JobHistory> findByEmployee(Employee employee);
}
