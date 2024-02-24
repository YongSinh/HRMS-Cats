package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.base.BaseApi;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/info/employee")
public class EmployeeController {
    //@ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/hello")
    public BaseApi<?> findAllAccountTypes() {

        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account types have been found")
                .timestamp(LocalDateTime.now())
                .data("Hello world")
                .build();
    }
}
