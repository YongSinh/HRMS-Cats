package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.EmployeeDtoRep;
import com.cats.informationmanagementservice.Dto.EmployeeDtoReq;
import com.cats.informationmanagementservice.model.Employee;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    Employee addPersonalData(EmployeeDtoReq employee);
    EmployeeDtoRep editPersonalData(EmployeeDtoReq employee, Long Id);
    List<EmployeeDtoRep> listEmployee();
    EmployeeDtoRep getEmployeeDtoRepById(Long Id);

    String uploadFile(MultipartFile file, Long emId, Integer type) throws IOException;

    Employee getPersonalDataById (Long Id);
}
;