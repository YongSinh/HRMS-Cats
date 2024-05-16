package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.Dto.DepartmentDtoRep;
import com.cats.informationmanagementservice.Dto.DepartmentDtoReq;
import com.cats.informationmanagementservice.Dto.FamilyDataDtoReq;
import com.cats.informationmanagementservice.base.BaseApi;
import com.cats.informationmanagementservice.model.Department;
import com.cats.informationmanagementservice.model.FamilyData;
import com.cats.informationmanagementservice.service.FamilyDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/info/familyData")
@RequiredArgsConstructor
public class FamilyDataController {
    private final FamilyDataService familyDataService;

    @PostMapping("/addFamilyData")
    public BaseApi<?> addFamilyData(@RequestBody FamilyDataDtoReq familyDataDtoReq) {
        FamilyData familyData = familyDataService.create(familyDataDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Family data have been created")
                .timestamp(LocalDateTime.now())
                .data(familyData)
                .build();
    }
    @PutMapping("/editFamilyData/{Id}")
    public BaseApi<?> editFamilyData(@RequestBody FamilyDataDtoReq familyDataDtoReq, @PathVariable Long Id) {
        FamilyData familyData = familyDataService.edit(familyDataDtoReq, Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Family data have been created")
                .timestamp(LocalDateTime.now())
                .data(familyData)
                .build();
    }
    @GetMapping("/getListFamilyData")
    public BaseApi<?> getListFamilyData() {
        List<FamilyData> familyData = familyDataService.getListFamilyData();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Family data have been found")
                .timestamp(LocalDateTime.now())
                .data(familyData)
                .build();
    }

    @GetMapping("/getListFamilyDataByEmId")
    public BaseApi<?> getListFamilyDataByEmId(@RequestParam Long emId) {
        List<FamilyData> familyData = familyDataService.getListFamilyDataByEmId(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Family data have been found")
                .timestamp(LocalDateTime.now())
                .data(familyData)
                .build();
    }


    @GetMapping("/getFamilyDataById/{id}")
    public BaseApi<?> listDepartment(@PathVariable Long id) {
        FamilyData familyData = familyDataService.getFamilyDataById(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("family Data have been found")
                .timestamp(LocalDateTime.now())
                .data(familyData)
                .build();
    }

    @DeleteMapping("/deleteFamilyData/{id}")
    @ResponseBody
    public ResponseEntity<?> getFamilyData(@PathVariable Long id) {
        familyDataService.delete(id);
        return new ResponseEntity<>("You have been delete the family data Id: "+ id, HttpStatus.OK);
    }

}
