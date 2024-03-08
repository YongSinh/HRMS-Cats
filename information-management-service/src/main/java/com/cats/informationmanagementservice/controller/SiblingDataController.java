package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.Dto.DepartmentDtoReq;
import com.cats.informationmanagementservice.Dto.SiblingDataDtoReq;
import com.cats.informationmanagementservice.base.BaseApi;
import com.cats.informationmanagementservice.model.Department;
import com.cats.informationmanagementservice.model.SiblingData;
import com.cats.informationmanagementservice.service.SiblingDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/info/siblingData")
@RequiredArgsConstructor
public class SiblingDataController {
    private final SiblingDataService siblingDataService;
    @PostMapping("/addSiblingData")
    public BaseApi<?> addSiblingData(@RequestBody SiblingDataDtoReq siblingDataDtoReq) {
        SiblingData siblingData = siblingDataService.create(siblingDataDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Sibling Data have been created")
                .timestamp(LocalDateTime.now())
                .data(siblingData)
                .build();
    }
    @GetMapping("/listSiblingData")
    public BaseApi<?> getListSiblingData() {
       List<SiblingData> siblingData = siblingDataService.getListSiblingData();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Sibling Data have been found")
                .timestamp(LocalDateTime.now())
                .data(siblingData)
                .build();
    }

    @GetMapping("/getSiblingDataById/{id}")
    public BaseApi<?> getListSiblingDataById(@PathVariable Long id) {
        SiblingData siblingData = siblingDataService.getSiblingData(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Sibling Data have been found")
                .timestamp(LocalDateTime.now())
                .data(siblingData)
                .build();
    }

    @PutMapping("/editSiblingData/{id}")
    public BaseApi<?> editSiblingData(@PathVariable Long id, @RequestBody  SiblingDataDtoReq siblingDataDtoReq) {
        SiblingData siblingData = siblingDataService.edit(siblingDataDtoReq,id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Sibling Data have been updated")
                .timestamp(LocalDateTime.now())
                .data(siblingData)
                .build();
    }
    @DeleteMapping("/deleteSiblingData/{id}")
    public BaseApi<?> deleteSiblingData(@PathVariable Long id) {
        SiblingData siblingData = siblingDataService.getSiblingData(id);
        siblingDataService.delete(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Sibling Data have been Deleted")
                .timestamp(LocalDateTime.now())
                .data(siblingData)
                .build();
    }
}
