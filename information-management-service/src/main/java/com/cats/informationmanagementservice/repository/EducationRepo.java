package com.cats.informationmanagementservice.repository;

import com.cats.informationmanagementservice.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepo extends JpaRepository<Education, Long> {
}
