package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.Dto.DepartmentDtoReq;
import com.cats.informationmanagementservice.base.BaseApi;
import com.cats.informationmanagementservice.model.Department;
import com.cats.informationmanagementservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/info/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/addDepartment")
    public BaseApi<?> addDepartment(@RequestBody DepartmentDtoReq departmentDtoReq) {
        Department department = departmentService.addDepartment(departmentDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account types have been found")
                .timestamp(LocalDateTime.now())
                .data(department)
                .build();
    }

    @PutMapping("/editDepartment/{id}")
    public BaseApi<?> editDepartment(@RequestBody DepartmentDtoReq departmentDtoReq, @PathVariable Long id) {
        Department department = departmentService.editDepartment(id, departmentDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account types have been found")
                .timestamp(LocalDateTime.now())
                .data(department)
                .build();
    }

    @GetMapping("/department")
    public BaseApi<?> listDepartment() {
        List<Department> department = departmentService.getListDepartment();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account types have been found")
                .timestamp(LocalDateTime.now())
                .data(department)
                .build();
    }

    @GetMapping("/departmentById/{id}")
    public BaseApi<?> listDepartment(@PathVariable Long id) {
        Department department = departmentService.getDepById(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account types have been found")
                .timestamp(LocalDateTime.now())
                .data(department)
                .build();
    }

}
