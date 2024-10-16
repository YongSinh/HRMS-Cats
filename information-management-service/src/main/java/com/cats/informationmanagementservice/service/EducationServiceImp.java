package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.EducationDtoReq;
import com.cats.informationmanagementservice.model.Education;
import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.JobHistory;
import com.cats.informationmanagementservice.repository.EducationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationServiceImp implements EducationService{
    private final EducationRepo educationRepo;
    private final EmployeeService employeeService;

    @Override
    public Education create(EducationDtoReq educationDtoReq) {
        Education education = new Education();
        education.setGPA(educationDtoReq.getGPA());
        education.setEduLevel(educationDtoReq.getEduLevel());
        education.setEduInstitution(educationDtoReq.getEduInstitution());
        education.setMajor(educationDtoReq.getMajor());
        education.setYearEnd(educationDtoReq.getYearEnd());
        if(educationDtoReq.getEmID() == null){
            throw new IllegalArgumentException("Employee at least");
        }
        Employee employee = employeeService.getPersonalDataById(educationDtoReq.getEmID());
        education.setEmployee(employee);
        educationRepo.save(education);
        return education;
    }

    @Override
    public Education edit(EducationDtoReq educationDtoReq, Long Id) {
        Education education = getEducationById(Id);
        education.setGPA(educationDtoReq.getGPA());
        education.setEduLevel(educationDtoReq.getEduLevel());
        education.setEduInstitution(educationDtoReq.getEduInstitution());
        education.setMajor(educationDtoReq.getMajor());
        education.setYearEnd(educationDtoReq.getYearEnd());
        if(educationDtoReq.getEmID() != null){
            Employee employee = employeeService.getPersonalDataById(educationDtoReq.getEmID());
            education.setEmployee(employee);
        }
        educationRepo.save(education);
        return education;
    }

    @Override
    public List<Education> getEducation() {
        return educationRepo.findAll();
    }

    @Override
    public List<Education> getEducationByEmId(Long emId) {
        Employee employee = employeeService.getPersonalDataById(emId);
        return educationRepo.findByEmployee(employee);
    }

    @Override
    public Education getEducationById(Long Id) {
        return educationRepo.findById(Id).orElseThrow(() ->
                new IllegalArgumentException("cannot find Education with id: " + Id));
    }

    @Override
    public void delete(Long Id) {
        Education education = getEducationById(Id);
        educationRepo.save(education);
    }
}
