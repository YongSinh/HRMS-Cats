package com.cats.informationmanagementservice.repository;

import com.cats.informationmanagementservice.model.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyContactRepo  extends JpaRepository<EmergencyContact, Long> {
}
