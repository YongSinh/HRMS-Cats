package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.EmployeeDtoRep;
import com.cats.informationmanagementservice.Dto.EmployeeDtoReq;
import com.cats.informationmanagementservice.Dto.EmployeeDtoReqEdit;
import com.cats.informationmanagementservice.Dto.EmployeeInfo;
import com.cats.informationmanagementservice.model.Employee;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {
    Employee addPersonalData(EmployeeDtoReq employee, MultipartFile file) throws IOException;

    EmployeeDtoRep editPersonalData(EmployeeDtoReqEdit employee, MultipartFile file) throws IOException;

    List<EmployeeDtoRep> listEmployee();

    Employee update(EmployeeDtoReqEdit employee);

    EmployeeInfo getEmpInfoByEmId(Long emId);

    List<EmployeeDtoRep> getEmployeeByDep(Long depId);

    List<EmployeeDtoRep> getEmployeeByUnderManger(Long emId);

    List<EmployeeDtoRep> getEmployeeByDepAndPos(Long depId, String posId);

    EmployeeDtoRep getEmployeeDtoRepById(Long Id);

    List<Long> getEmployeeByDepGetOnlyEmId(Long depId);

    List<Long> getEmployeeByDepAndPosId(Long depId, String posId);

    void uploadFile(MultipartFile file, Long emId, Integer type, LocalDate date, Integer serviceType) throws IOException;

    void uploadUpdateFile(MultipartFile file, Long emId, Integer type, LocalDate date, Integer serviceType, String fileId) throws IOException;

    Employee getPersonalDataById(Long Id);

    void deleteEmpInfo(Long emId);

    List<Long> getListEmpId();

    String getEmpFullName(Long emId);

}
;