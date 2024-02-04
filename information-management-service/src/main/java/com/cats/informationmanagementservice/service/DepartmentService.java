package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.DepartmentDtoReq;
import com.cats.informationmanagementservice.model.Department;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;

import java.util.List;

public interface DepartmentService {
    Department getDepById(Long Id);
    Department addDepartment(DepartmentDtoReq departmentDtoReq);
    List<Department> getListDepartment();
}
