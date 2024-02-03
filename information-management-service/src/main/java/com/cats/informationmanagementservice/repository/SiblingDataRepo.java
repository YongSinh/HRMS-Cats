package com.cats.informationmanagementservice.repository;

import com.cats.informationmanagementservice.model.SiblingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiblingDataRepo extends JpaRepository<SiblingData, Long> {
}
