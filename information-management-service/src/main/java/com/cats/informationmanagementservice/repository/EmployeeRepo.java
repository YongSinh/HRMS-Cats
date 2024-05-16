package com.cats.informationmanagementservice.repository;

import com.cats.informationmanagementservice.model.Department;
import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartment(Department department);
    List<Employee> findByDepartmentAndPosition(Department department, Position position);
}
