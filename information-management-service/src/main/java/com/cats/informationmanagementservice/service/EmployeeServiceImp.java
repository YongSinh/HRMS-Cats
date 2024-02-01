package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.repository.EmployeeRepo;
import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService{
    private final EmployeeRepo employeeRepo;

}
