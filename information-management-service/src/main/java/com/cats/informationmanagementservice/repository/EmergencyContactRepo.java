package com.cats.informationmanagementservice.repository;

import com.cats.informationmanagementservice.model.EmergencyContact;
import com.cats.informationmanagementservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyContactRepo  extends JpaRepository<EmergencyContact, Long> {
    List<EmergencyContact> findByEmployee(Employee employee);
}
