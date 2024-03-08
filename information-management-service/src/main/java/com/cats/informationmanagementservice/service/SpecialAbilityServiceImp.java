package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.SpecialAbilityDtoReq;
import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.SpecialAbility;
import com.cats.informationmanagementservice.repository.SpecialAbilityRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialAbilityServiceImp implements SpecialAbilityService{
    private final SpecialAbilityRepo specialAbilityRepo;
    private final EmployeeService employeeService;

    @Override
    public SpecialAbility create(SpecialAbilityDtoReq specialAbilityDtoReq) {
        SpecialAbility specialAbility = new SpecialAbility();
        specialAbility.setForeignLanguages(specialAbilityDtoReq.getForeignLanguages());
        specialAbility.setSpeaking(specialAbilityDtoReq.getSpeaking());
        specialAbility.setWriting(specialAbilityDtoReq.getWriting());
        specialAbility.setListening(specialAbilityDtoReq.getListening());
        specialAbility.setReading(specialAbilityDtoReq.getReading());
        if (specialAbilityDtoReq.getEmpId() ==null){
            throw new IllegalArgumentException("Employee at least");
        }
        Employee employee = employeeService.getPersonalDataById(specialAbilityDtoReq.getEmpId());
        specialAbility.setEmployee(employee);
        return specialAbilityRepo.save(specialAbility);
    }

    @Override
    public SpecialAbility edit(SpecialAbilityDtoReq specialAbilityDtoReq, Long id) {
        SpecialAbility specialAbility = getSpecialAbilityById(id);
        specialAbility.setForeignLanguages(specialAbilityDtoReq.getForeignLanguages());
        specialAbility.setSpeaking(specialAbilityDtoReq.getSpeaking());
        specialAbility.setWriting(specialAbilityDtoReq.getWriting());
        specialAbility.setListening(specialAbilityDtoReq.getListening());
        specialAbility.setReading(specialAbilityDtoReq.getReading());
        if (specialAbilityDtoReq.getEmpId() !=null){
            Employee employee = employeeService.getPersonalDataById(specialAbilityDtoReq.getEmpId());
            specialAbility.setEmployee(employee);
        }
        return specialAbilityRepo.save(specialAbility);
    }

    @Override
    public SpecialAbility getSpecialAbilityById(Long id) {
        return specialAbilityRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("cannot find Special Ability with id: " + id));
    }

    @Override
    public void delete(Long id) {
        SpecialAbility specialAbility = getSpecialAbilityById(id);
        specialAbilityRepo.delete(specialAbility);
    }

    @Override
    public List<SpecialAbility> getListSpecialAbility() {
        return specialAbilityRepo.findAll();
    }
}
