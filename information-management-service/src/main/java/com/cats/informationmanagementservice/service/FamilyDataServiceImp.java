package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.FamilyDataDtoReq;
import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.FamilyData;
import com.cats.informationmanagementservice.repository.FamilyDataRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyDataServiceImp implements FamilyDataService{

    private final FamilyDataRepo familyDataRepo;
    private final EmployeeService employeeService;
    @Override
    public FamilyData create(FamilyDataDtoReq familyDataDtoReq) {
        FamilyData familyData = new FamilyData();
        familyData.setFatherName(familyDataDtoReq.getFatherName());
        familyData.setFatherAddress(familyDataDtoReq.getFatherAddress());
        familyData.setFatherOccupation(familyDataDtoReq.getFatherOccupation());
        familyData.setFatherPhoneNum(familyDataDtoReq.getFatherPhoneNum());
        familyData.setMotherName(familyDataDtoReq.getMotherName());
        familyData.setMotherAddress(familyDataDtoReq.getMotherAddress());
        familyData.setMotherOccupation(familyDataDtoReq.getMotherOccupation());
        familyData.setMotherPhoneNum(familyDataDtoReq.getMotherPhoneNum());
        if(familyDataDtoReq.getEmId() == null){
            throw new IllegalArgumentException("Employee at least");
        }
        Employee employee = employeeService.getPersonalDataById(familyDataDtoReq.getEmId());
        familyData.setEmployee(employee);
        return familyDataRepo.save(familyData);
    }

    @Override
    public FamilyData edit(FamilyDataDtoReq familyDataDtoReq, Long Id) {
        FamilyData familyData = getFamilyDataById(Id);
        familyData.setFatherName(familyDataDtoReq.getFatherName());
        familyData.setFatherAddress(familyDataDtoReq.getFatherAddress());
        familyData.setFatherOccupation(familyDataDtoReq.getFatherOccupation());
        familyData.setFatherPhoneNum(familyDataDtoReq.getFatherPhoneNum());
        familyData.setMotherName(familyDataDtoReq.getMotherName());
        familyData.setMotherAddress(familyDataDtoReq.getMotherAddress());
        familyData.setMotherOccupation(familyDataDtoReq.getMotherOccupation());
        familyData.setMotherPhoneNum(familyDataDtoReq.getMotherPhoneNum());
        if(familyDataDtoReq.getEmId() != null){
            Employee employee = employeeService.getPersonalDataById(familyDataDtoReq.getEmId());
            familyData.setEmployee(employee);
        }
        return familyDataRepo.save(familyData);
    }

    @Override
    public FamilyData getFamilyDataById(Long Id) {
        return familyDataRepo.findById(Id).orElseThrow(() ->
                new IllegalArgumentException("cannot find Family Data with id: " + Id));
    }

    @Override
    public List<FamilyData> getListFamilyData() {
        return familyDataRepo.findAll();
    }

    @Override
    public void delete(Long Id) {
        FamilyData familyData = getFamilyDataById(Id);
        familyDataRepo.delete(familyData);
    }
}
