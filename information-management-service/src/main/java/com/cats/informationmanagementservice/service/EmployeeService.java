package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.EmployeeDtoRep;
import com.cats.informationmanagementservice.Dto.EmployeeDtoReq;
import com.cats.informationmanagementservice.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee addPersonalData(EmployeeDtoReq employee);
    EmployeeDtoRep editPersonalData(EmployeeDtoReq employee, Long Id);
    List<EmployeeDtoRep> listEmployee();
    EmployeeDtoRep getEmployeeDtoRepById(Long Id);


    Employee getPersonalDataById (Long Id);
}
