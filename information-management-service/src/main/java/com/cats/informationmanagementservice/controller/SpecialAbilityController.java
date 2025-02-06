package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.Dto.SpecialAbilityDtoReq;
import com.cats.informationmanagementservice.base.BaseApi;
import com.cats.informationmanagementservice.model.SpecialAbility;
import com.cats.informationmanagementservice.service.SpecialAbilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/info/specialAbility")
@RequiredArgsConstructor
public class SpecialAbilityController {
    private final SpecialAbilityService specialAbilityService;

    @PostMapping("/add")
    public BaseApi<?> addSpecialAbility(@RequestBody SpecialAbilityDtoReq specialAbilityDtoReq) {
        SpecialAbility specialAbility = specialAbilityService.create(specialAbilityDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Special Ability Data have been created")
                .timestamp(LocalDateTime.now())
                .data(specialAbility)
                .build();
    }

    @PostMapping("/update")
    public BaseApi<?> updateSpecialAbility(@RequestBody SpecialAbilityDtoReq specialAbilityDtoReq, @RequestParam Long Id) {
        SpecialAbility specialAbility = specialAbilityService.edit(specialAbilityDtoReq, Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Special Ability Data have been updated")
                .timestamp(LocalDateTime.now())
                .data(specialAbility)
                .build();
    }

    @GetMapping("/getSpecialAbilityById")
    public BaseApi<?> getSpecialAbilityById(@RequestParam Long Id) {
        SpecialAbility specialAbility = specialAbilityService.getSpecialAbilityById(Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Special Ability Data have been found")
                .timestamp(LocalDateTime.now())
                .data(specialAbility)
                .build();
    }

    @GetMapping("/getListSpecialAbility")
    public BaseApi<?> getListSpecialAbility() {
        List<SpecialAbility> specialAbilityList = specialAbilityService.getListSpecialAbility();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Special Ability Data have been found")
                .timestamp(LocalDateTime.now())
                .data(specialAbilityList)
                .build();
    }

    @GetMapping("/getListSpecialAbilityByEmId")
    public BaseApi<?> getListSpecialAbilityByEmId(@RequestParam Long emId) {
        List<SpecialAbility> specialAbilityList = specialAbilityService.getListSpecialAbilityByEmId(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Special Ability Data have been found")
                .timestamp(LocalDateTime.now())
                .data(specialAbilityList)
                .build();
    }

    @DeleteMapping("/delete")
    public BaseApi<?> deleteSpecialAbility(@RequestParam Long spId) {
        specialAbilityService.delete(spId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Special Ability Data have been delete")
                .timestamp(LocalDateTime.now())
                .data("Special Ability Data have been delete")
                .build();
    }
}
