package com.cats.informationmanagementservice.Dto;

import com.cats.informationmanagementservice.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class mapper {

    public static PositionDtoRep PosToPositionResponseDto(Position position) {
        PositionDtoRep positionDtoRep = new PositionDtoRep();
        positionDtoRep.setId(position.getPosId());
        positionDtoRep.setPosName(position.getPosName());
        positionDtoRep.setDepName(position.getDepartment().getDepName());
        return positionDtoRep;
    }
    public static List<PositionDtoRep> PosToPositionResponseDtos(List<Position> positions) {
        List<PositionDtoRep> positionDtoReps = new ArrayList<>();
        for (Position position: positions) {
            positionDtoReps.add(PosToPositionResponseDto(position));
        }
        return positionDtoReps;
    }

    public static DepartmentDtoRep DepToDepartmentDtoRep(Department department){
        DepartmentDtoRep departmentDtoRep = new DepartmentDtoRep();
        departmentDtoRep.setId(department.getDepId());
        departmentDtoRep.setDepName(department.getDepName());
        return  departmentDtoRep;
    }
    public static EmployeeDtoRep EmployeeDtoRepToEmployeeDtoRep(Employee employee){
        EmployeeDtoRep emp = new EmployeeDtoRep();
        emp.setEmpId(employee.getEmpId());
        emp.setEmpCode(employee.getEmpCode());
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setEmail(employee.getEmail());
        emp.setBirthDate(employee.getBirthDate());
        emp.setAge(employee.getAge());
        emp.setSex(employee.getSex());
        emp.setHeight(employee.getHeight());
        emp.setWeight(employee.getWeight());
        emp.setAddress(employee.getAddress());
        emp.setEmpDate(employee.getEmpDate());
        emp.setJoinDate(employee.getJoinDate());
        emp.setMangerId(employee.getMangerId());
        emp.setLocation(employee.getLocation());
        emp.setMaritalStats(employee.getMaritalStats());
        emp.setNationality(employee.getNationality());
        emp.setWorkType(employee.getWorkType());
        emp.setReligion(employee.getReligion());
        emp.setIdCard(employee.getIdCard());
        emp.setPassport(employee.getPassport());
        emp.setRemark(employee.getRemark());
        emp.setGovOfficer(employee.getGovOfficer());
        emp.setGovTel(employee.getGovTel());
        emp.setGovPosition(employee.getGovPosition());
        emp.setGovAddress(employee.getGovAddress());
        emp.setDepId(employee.getDepartment().getDepName());
        emp.setPosId(employee.getPosition().getPosName());

        List<EducationDtoRep> educationDtoRepList = new ArrayList<>();
        List<Education> existingEducationDtoReps = employee.getEducations();
        if (existingEducationDtoReps != null) {
            educationDtoRepList = existingEducationDtoReps.stream()
                    .map(educationDtoRep -> {
                        EducationDtoRep dtoRep = new EducationDtoRep();
                        dtoRep.setId(educationDtoRep.getId());
                        dtoRep.setGPA(educationDtoRep.getGPA());
                        dtoRep.setEduLevel(educationDtoRep.getEduLevel());
                        dtoRep.setEduInstitution(educationDtoRep.getEduInstitution());
                        dtoRep.setMajor(educationDtoRep.getMajor());
                        dtoRep.setYearEnd(educationDtoRep.getYearEnd());
                        return dtoRep;
                    })
                    .collect(Collectors.toList());
        }
        emp.setEducationDtoReps(educationDtoRepList);
        List<JobHistory> jobHistoryDtoReps = employee.getJobHistories();
        List<EmergencyContact> emergencyContacts = employee.getEmergencyContacts();
        List<SiblingData> siblingData = employee.getSiblingData();
        emp.setSiblingData(siblingData);
        emp.setEmergencyContacts(emergencyContacts);
        emp.setJobHistoryDtoReps(jobHistoryDtoReps);

        return emp;
    }
    public static List<EmployeeDtoRep> EmployeeDtoRepToEmployeeDtoReps(List<Employee> employees) {
        List<EmployeeDtoRep> employeeDtoReps = new ArrayList<>();
        for (Employee employee: employees) {
            employeeDtoReps.add(EmployeeDtoRepToEmployeeDtoRep(employee));
        }
        return employeeDtoReps;
    }
}
