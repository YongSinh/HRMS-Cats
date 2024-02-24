package com.cats.informationmanagementservice.repository;

import com.cats.informationmanagementservice.model.JobHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobHistoryRepo extends JpaRepository<JobHistory,Long> {
}
