package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.EmployeeDtoRep;
import com.cats.informationmanagementservice.Dto.EmployeeDtoReq;
import com.cats.informationmanagementservice.model.Employee;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {
    Employee addPersonalData(EmployeeDtoReq employee, MultipartFile file) throws IOException;
    EmployeeDtoRep editPersonalData(EmployeeDtoReq employee, Long Id);
    List<EmployeeDtoRep> listEmployee();

    List<EmployeeDtoRep> getEmployeeByDep(Long depId);
    List<EmployeeDtoRep> getEmployeeByUnderManger(Long emId);
    List<EmployeeDtoRep> getEmployeeByDepAndPos(Long depId, String posId);
    EmployeeDtoRep getEmployeeDtoRepById(Long Id);
    List<Long> getEmployeeByDepGetOnlyEmId(Long depId);
    List<Long> getEmployeeByDepAndPosId(Long depId, String posId);
    String uploadFile(MultipartFile file, Long emId, Integer type,  LocalDate date) throws IOException;

    Employee getPersonalDataById (Long Id);

    void deleteEmpInfo(Long emId);

}
;