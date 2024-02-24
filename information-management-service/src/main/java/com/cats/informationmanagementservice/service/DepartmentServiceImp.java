package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.DepartmentDtoRep;
import com.cats.informationmanagementservice.Dto.DepartmentDtoReq;
import com.cats.informationmanagementservice.Dto.mapper;
import com.cats.informationmanagementservice.model.Department;
import com.cats.informationmanagementservice.repository.DepartmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImp implements DepartmentService{
    private final DepartmentRepo departmentRepo;
    @Override
    public Department getDepById(Long Id) {
        return departmentRepo.findById(Id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Department with id: " + Id + " could not be found"));
    }

    @Override
    public Department addDepartment(DepartmentDtoReq departmentDtoReq) {
        Department department = new Department();
        department.setDepName(departmentDtoReq.getDepName());
        return departmentRepo.save(department);
    }

    @Override
    public DepartmentDtoRep deleteDepartment(Long Id) {
        Department department = getDepById(Id);
        departmentRepo.delete(department);
        return mapper.DepToDepartmentDtoRep(department);
    }

    @Override
    public Department editDepartment(Long Id, DepartmentDtoReq departmentDtoReq) {
        Department department = getDepById(Id);
        department.setDepName(departmentDtoReq.getDepName());
        return departmentRepo.save(department);
    }

    @Override
    public List<Department> getListDepartment() {
        return departmentRepo.findAll();
    }
}
