package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.EmployeeDtoReq;
import com.cats.informationmanagementservice.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee addPersonalData(EmployeeDtoReq employee);
    Employee editPersonalData(EmployeeDtoReq employee, Long Id);
    List<Employee> listEmployee();

    Employee getPersonalDataById (Long Id);
}
