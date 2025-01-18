package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.Dto.EmployeeDtoRep;
import com.cats.informationmanagementservice.Dto.EmployeeDtoReq;
import com.cats.informationmanagementservice.Dto.EmployeeDtoReqEdit;
import com.cats.informationmanagementservice.Dto.EmployeeInfo;
import com.cats.informationmanagementservice.base.BaseApi;
import com.cats.informationmanagementservice.model.Employee;
import com.cats.informationmanagementservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/info/employee")
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;

    //@ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/delete")
    public BaseApi<?> deleteEmpInfo(@RequestParam Long emId) {
        employeeService.deleteEmpInfo(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee have been delete")
                .timestamp(LocalDateTime.now())
                .data("Hello world")
                .build();
    }

    @GetMapping("/listEmployeeByDep")
    public BaseApi<?> listEmployeeByDep(@RequestParam Long depId) {
        List<EmployeeDtoRep> employee = employeeService.getEmployeeByDep(depId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("list Employee have been found")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }

    @GetMapping("/listEmployeeByDepOnlyEmId")
    public BaseApi<?> listEmployeeByDepOnlyEmId(@RequestParam Long depId) {
        List<Long> employee = employeeService.getEmployeeByDepGetOnlyEmId(depId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("list Employee Id have been found")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }


    @GetMapping("/getEmployeeByUnderManger")
    public BaseApi<?> getEmployeeByUnderManger(@RequestParam Long emId) {
        List<EmployeeDtoRep> employee = employeeService.getEmployeeByUnderManger(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("list Employee Id have been found")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }

    @GetMapping("/getEmpInfoById")
    public BaseApi<?> getEmpInfoById(@RequestParam Long emId) {
        EmployeeInfo employee = employeeService.getEmpInfoByEmId(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee Id have been found")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }

    @GetMapping("/getEmployeeByUnderMangerOnlyEmId")
    public BaseApi<?> getEmployeeByUnderMangerOnlyEmId(@RequestParam Long emId) {
        List<EmployeeDtoRep> employee = employeeService.getEmployeeByUnderManger(emId);
        List<Long> idList = new ArrayList<>();
        for (EmployeeDtoRep employeeDtoRep : employee) {
            idList.add(employeeDtoRep.getEmpId());
        }
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("list Employee Id have been found")
                .timestamp(LocalDateTime.now())
                .data(idList)
                .build();
    }

    @GetMapping("/getEmployeeName")
    public BaseApi<?> getEmpFullName(@RequestParam Long emId) {
        String employee = employeeService.getEmpFullName(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("list Employee Id have been found")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }



    @GetMapping("/listEmployeeByDepAndPosOnlyEmId")
    public BaseApi<?> listEmployeeByDepAndPosOnlyEmId(@RequestParam Long depId, @RequestParam String posId) {
        List<Long> employee = employeeService.getEmployeeByDepAndPosId(depId, posId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("list Employee data have been found")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }

    @GetMapping("/listEmployeeByDepAndPos")
    public BaseApi<?> listEmployeeByDep(@RequestParam Long depId, @RequestParam String posId) {
        List<EmployeeDtoRep> employee = employeeService.getEmployeeByDepAndPos(depId, posId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("list Employee data have been found")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }

    @PostMapping(value = "/addEmployee", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseApi<?> addEmployeeData(@RequestPart("body") EmployeeDtoReq employeeDtoReq,
                                      @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        // Handle the case when no file is uploaded
        System.out.println(file);
        Employee employee = employeeService.addPersonalData(employeeDtoReq, file);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee data have been created")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }


    @GetMapping("/listEmployee")
    public BaseApi<?> listEmployee() {
        List<EmployeeDtoRep> employee = employeeService.listEmployee();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee data have been found")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }

    @GetMapping("/listEmployeeId")
    public BaseApi<?> listEmployeeId() {
        List<Long> employee = employeeService.getListEmpId();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee data have been found")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }

    @GetMapping("/getEmployeeById/{Id}")
    public BaseApi<?> getEmployeeById(@PathVariable Long Id) {
        EmployeeDtoRep employee = employeeService.getEmployeeDtoRepById(Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee data have been found")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }

    @RequestMapping(value = "/editEmployee", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public BaseApi<?> editEmployee(@RequestPart("body") EmployeeDtoReqEdit employeeDtoReq,
                                   @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        EmployeeDtoRep employee = employeeService.editPersonalData(employeeDtoReq, file);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee data have been updated")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }

    @RequestMapping(value = "/user/editInfo", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public BaseApi<?> userEditInfo(@RequestPart("body") EmployeeDtoReqEdit employeeDtoReq,
                                   @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        Employee employee = employeeService.userUpdateInfo(employeeDtoReq, file);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee data have been updated")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }


    @RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
    public BaseApi<?> updateEmployee(@RequestBody EmployeeDtoReqEdit employeeDtoReq) throws IOException {
        Employee employee = employeeService.update(employeeDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Employee data have been updated")
                .timestamp(LocalDateTime.now())
                .data(employee)
                .build();
    }

    //    @CircuitBreaker(name = "attendance",fallbackMethod = "fallbackMethod")
//    @TimeLimiter(name = "attendance")
//    @Retry(name = "attendance")
//    @RequestMapping(value = "/file/upload", method = RequestMethod.POST, consumes = { "multipart/form-data"})
//    public CompletableFuture<?> uploadFile(@RequestPart("file") @Valid MultipartFile file) throws IOException {
//
//            LocalDate localDate = LocalDate.now();
//            employeeService.uploadFile(file,2431L, 1, localDate, 1);
//            String message = "Uploaded the file successfully: " + file.getOriginalFilename();
//        return CompletableFuture.supplyAsync(() ->message);
//
//    }
    public CompletableFuture<?> fallbackMethod(MultipartFile file, RuntimeException runtimeException) {
        System.out.println("Cannot Place Order Executing Fallback logic");
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after some time!");
    }
}
