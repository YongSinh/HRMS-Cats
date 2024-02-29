package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.SiblingDataDtoReq;
import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.SiblingData;
import com.cats.informationmanagementservice.repository.SiblingDataRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiblingDataServiceImp implements SiblingDataService{
    private final SiblingDataRepo siblingDataRepo;
    private final EmployeeService employeeService;
    @Override
    public List<SiblingData> getListSiblingData() {
        return siblingDataRepo.findAll();
    }

    @Override
    public SiblingData getSiblingData(Long id) {
        return siblingDataRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("cannot find Education with id: " + id));
    }

    @Override
    public SiblingData create(SiblingDataDtoReq siblingDataDtoReq) {
        SiblingData siblingData = new SiblingData();
        siblingData.setFirstName(siblingDataDtoReq.getFirstName());
        siblingData.setLastName(siblingDataDtoReq.getLastName());
        siblingData.setAge(siblingDataDtoReq.getAge());
        siblingData.setSex(siblingDataDtoReq.getSex());
        siblingData.setEducation(siblingDataDtoReq.getEducation());
        siblingData.setOccupation(siblingDataDtoReq.getOccupation());
        siblingData.setPosition(siblingDataDtoReq.getPosition());
        siblingData.setOffice(siblingDataDtoReq.getOffice());
        siblingData.setMaritalStats(siblingDataDtoReq.getMaritalStats());
        if(siblingDataDtoReq.getEmpId() == null){
            throw new IllegalArgumentException("Employee at least");
        }
        Employee employee = employeeService.getPersonalDataById(siblingDataDtoReq.getEmpId());
        siblingData.setEmployee(employee);
        return siblingDataRepo.save(siblingData);
    }

    @Override
    public SiblingData edit(SiblingDataDtoReq siblingDataDtoReq, Long id) {
        SiblingData siblingData = getSiblingData(id);
        siblingData.setFirstName(siblingDataDtoReq.getFirstName());
        siblingData.setLastName(siblingDataDtoReq.getLastName());
        siblingData.setAge(siblingDataDtoReq.getAge());
        siblingData.setSex(siblingDataDtoReq.getSex());
        siblingData.setEducation(siblingDataDtoReq.getEducation());
        siblingData.setOccupation(siblingDataDtoReq.getOccupation());
        siblingData.setPosition(siblingDataDtoReq.getPosition());
        siblingData.setOffice(siblingDataDtoReq.getOffice());
        siblingData.setMaritalStats(siblingDataDtoReq.getMaritalStats());
        if(siblingDataDtoReq.getEmpId() != null){
            Employee employee = employeeService.getPersonalDataById(siblingDataDtoReq.getEmpId());
            siblingData.setEmployee(employee);
        }
        return siblingDataRepo.save(siblingData);
    }
}
