package com.cats.informationmanagementservice.repository;

import com.cats.informationmanagementservice.model.Department;
import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment(Department department);
    Optional<Employee> findByEmpId(Long empId);
    @Query(nativeQuery = true, value = "CALL GetEmployeeHierarchy(:manId, :depId)")
    List<Employee> callGetEmployeeHierarchyProcedure(@Param("manId") Long manId, @Param("depId") Long depId);

    List<Employee> findByDepartmentAndPosition(Department department, Position position);
    @Query("select a.empId from Employee as a")
    List<Long> findEmployeeId();
}
