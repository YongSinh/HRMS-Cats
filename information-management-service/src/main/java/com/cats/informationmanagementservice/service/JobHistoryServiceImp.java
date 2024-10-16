package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.JobHistoryDtoReq;
import com.cats.informationmanagementservice.model.Department;
import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.JobHistory;
import com.cats.informationmanagementservice.model.Position;
import com.cats.informationmanagementservice.repository.JobHistoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobHistoryServiceImp implements JobHistoryService{

    private final JobHistoryRepo jobHistoryRepo;
    private final EmployeeService employeeService;
    @Override
    public JobHistory create(JobHistoryDtoReq jobHistoryDtoReq) {
        JobHistory jobHistory = new JobHistory();
        jobHistory.setJobTitle(jobHistoryDtoReq.getJobTitle());
        jobHistory.setDepartment(jobHistoryDtoReq.getDepartment());
        jobHistory.setStartDate(jobHistoryDtoReq.getStartDate());
        jobHistory.setEndDate(jobHistoryDtoReq.getEndDate());
        if(jobHistoryDtoReq.getEmpId() == null){
            throw new IllegalArgumentException("Position at least on Department ");
        }
        Employee employee = employeeService.getPersonalDataById(jobHistoryDtoReq.getEmpId());
        jobHistory.setEmployee(employee);
        jobHistoryRepo.save(jobHistory);
        return jobHistory;
    }

    @Override
    public JobHistory edit(JobHistoryDtoReq jobHistoryDtoReq, Long Id) {
        JobHistory jobHistory = getById(Id);
        jobHistory.setJobTitle(jobHistoryDtoReq.getJobTitle());
        jobHistory.setDepartment(jobHistoryDtoReq.getDepartment());
        jobHistory.setStartDate(jobHistoryDtoReq.getStartDate());
        jobHistory.setEndDate(jobHistoryDtoReq.getEndDate());
        if(jobHistoryDtoReq.getEmpId() != null){
            Employee employee = employeeService.getPersonalDataById(jobHistoryDtoReq.getEmpId());
            jobHistory.setEmployee(employee);
        }
        jobHistoryRepo.save(jobHistory);
        return jobHistory;
    }

    @Override
    public JobHistory getById(Long Id) {
        return jobHistoryRepo.findById(Id).orElseThrow(() ->
                new IllegalArgumentException("cannot find Job History with id: " + Id));
    }

    @Override
    public void delete(Long Id) {
        JobHistory jobHistory = getById(Id);
        jobHistoryRepo.delete(jobHistory);
    }

    @Override
    public List<JobHistory> getListJobHistory() {
        return jobHistoryRepo.findAll();
    }

    @Override
    public List<JobHistory> getListJobHistoryBy(Long emId) {
        Employee employee = employeeService.getPersonalDataById(emId);
        return jobHistoryRepo.findByEmployee(employee);
    }
}
