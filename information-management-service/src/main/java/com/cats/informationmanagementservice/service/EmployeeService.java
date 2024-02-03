package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee addPersonalData(Employee employee);
    Employee editPersonalData(Employee employee, Long Id);
    List<Employee> listEmployee();

    Employee getPersonalDataById (Long Id);
}
