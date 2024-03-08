package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.EmployeeDtoRep;
import com.cats.informationmanagementservice.Dto.EmployeeDtoReq;
import com.cats.informationmanagementservice.Dto.mapper;
import com.cats.informationmanagementservice.model.Department;
import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.model.Position;
import com.cats.informationmanagementservice.repository.EmployeeRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService{
    private final EmployeeRepo employeeRepo;
    private final PositionService positionService;
    private final DepartmentService departmentService;

    @Transactional
    @Override
    public Employee addPersonalData(EmployeeDtoReq employee) {
        Employee emp = new Employee();
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
        if (employee.getPosId() == null){
            throw new IllegalArgumentException("Employee at least on Position ");
        }
        Position position = positionService.getPositionById(employee.getPosId());
        emp.setPosition(position);
        if (employee.getDepId() == null){
            throw new IllegalArgumentException("Employee at least on Position ");
        }
        Department department = departmentService.getDepById(employee.getDepId());
        emp.setDepartment(department);
        return employeeRepo.save(emp);
    }

    @Override
    public EmployeeDtoRep editPersonalData(EmployeeDtoReq employee, Long Id) {
        Employee emp = getPersonalDataById(Id);
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
        if (employee.getPosId() != null){
            Position position = positionService.getPositionById(employee.getPosId());
            emp.setPosition(position);
        }
        if (employee.getDepId() != null){
            Department department = departmentService.getDepById(employee.getDepId());
            emp.setDepartment(department);
        }
        employeeRepo.save(emp);
        return mapper.EmployeeDtoRepToEmployeeDtoRep(emp);
    }

    @Override
    public List<EmployeeDtoRep> listEmployee() {
        List<Employee> employees = StreamSupport
                .stream(employeeRepo.findAll().spliterator(), false)
                .toList();
        return mapper.EmployeeDtoRepToEmployeeDtoReps(employees);
    }

    @Override
    public EmployeeDtoRep getEmployeeDtoRepById(Long Id) {
        return mapper.EmployeeDtoRepToEmployeeDtoRep(getPersonalDataById(Id));
    }

    @Override
    public Employee getPersonalDataById(Long Id) {
        return employeeRepo.findById(Id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Employee with id: " + Id + " could not be found"));
    }
}

