package com.cats.informationmanagementservice.repository;

import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.SpecialAbility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialAbilityRepo extends JpaRepository<SpecialAbility, Long> {

    List<SpecialAbility> findByEmployee(Employee employee);
}
