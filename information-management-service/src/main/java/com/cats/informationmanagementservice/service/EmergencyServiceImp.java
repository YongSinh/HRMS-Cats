package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.EmergencyContactDtoReq;
import com.cats.informationmanagementservice.model.EmergencyContact;
import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.repository.EmergencyContactRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmergencyServiceImp implements EmergencyService {
    private final EmergencyContactRepo emergencyContactRepo;
    private final EmployeeService employeeService;

    @Override
    public EmergencyContact create(EmergencyContactDtoReq emergencyContactDtoReq) {
        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.setFullName(emergencyContactDtoReq.getFullName());
        emergencyContact.setRelationship(emergencyContactDtoReq.getRelationship());
        emergencyContact.setAddress(emergencyContactDtoReq.getAddress());
        emergencyContact.setTel(emergencyContactDtoReq.getTel());
        emergencyContact.setOfficeAddress(emergencyContactDtoReq.getOfficeAddress());
        emergencyContact.setOfficeTel(emergencyContactDtoReq.getOfficeTel());
        if (emergencyContactDtoReq.getEmpId() == null) {
            throw new IllegalArgumentException("Employee at least");
        }
        Employee employee = employeeService.getPersonalDataById(emergencyContactDtoReq.getEmpId());
        emergencyContact.setEmployee(employee);
        emergencyContactRepo.save(emergencyContact);
        return emergencyContact;
    }

    @Override
    public EmergencyContact edit(EmergencyContactDtoReq emergencyContactDtoReq, Long Id) {
        EmergencyContact emergencyContact = getEmergencyContactById(Id);
        emergencyContact.setFullName(emergencyContactDtoReq.getFullName());
        emergencyContact.setRelationship(emergencyContactDtoReq.getRelationship());
        emergencyContact.setAddress(emergencyContactDtoReq.getAddress());
        emergencyContact.setTel(emergencyContactDtoReq.getTel());
        emergencyContact.setOfficeAddress(emergencyContactDtoReq.getOfficeAddress());
        emergencyContact.setOfficeTel(emergencyContactDtoReq.getOfficeTel());
        if (emergencyContactDtoReq.getEmpId() != null) {
            Employee employee = employeeService.getPersonalDataById(emergencyContactDtoReq.getEmpId());
            emergencyContact.setEmployee(employee);
        }
        emergencyContactRepo.save(emergencyContact);
        return emergencyContact;
    }

    @Override
    public List<EmergencyContact> getList() {
        return emergencyContactRepo.findAll();
    }

    @Override
    public List<EmergencyContact> getListEmergencyContactByEmId(Long emId) {
        Employee employee = employeeService.getPersonalDataById(emId);
        return emergencyContactRepo.findByEmployee(employee);
    }

    @Override
    public EmergencyContact getEmergencyContactById(Long Id) {
        return emergencyContactRepo.findById(Id).orElseThrow(() ->
                new IllegalArgumentException("cannot find Emergency Contact with id: " + Id));
    }

    @Override
    public void delete(Long Id) {
        EmergencyContact emergencyContact = getEmergencyContactById(Id);
        emergencyContactRepo.delete(emergencyContact);
    }
}
