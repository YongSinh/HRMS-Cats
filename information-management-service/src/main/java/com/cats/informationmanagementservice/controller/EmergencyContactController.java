package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.Dto.EducationDtoReq;
import com.cats.informationmanagementservice.base.BaseApi;
import com.cats.informationmanagementservice.model.Education;
import com.cats.informationmanagementservice.service.EducationService;
import com.cats.informationmanagementservice.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/info/emergencyContact")
@RequiredArgsConstructor
public class EmergencyContactController {

    private final EducationService educationService;
    private final EmergencyService emergencyService;
    @PostMapping("/addEducation")
    public BaseApi<?> addEducation(@RequestBody EducationDtoReq educationDtoReq ) {
      Education education = educationService.create(educationDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Education have been created")
                .timestamp(LocalDateTime.now())
                .data(education)
                .build();
    }

    @PutMapping("/editJobHistory/{Id}")
    public BaseApi<?> editJobHistory(@RequestBody EducationDtoReq educationDtoReq , @PathVariable Long Id) {
        Education education = educationService.edit(educationDtoReq, Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Education have been updated")
                .timestamp(LocalDateTime.now())
                .data(education)
                .build();
    }

    @GetMapping("/education")
    public BaseApi<?> jobHistories() {
        List<Education> educations = educationService.getEducation();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Job history have been found")
                .timestamp(LocalDateTime.now())
                .data(educations)
                .build();
    }
    @DeleteMapping("/deleteEducation/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteEducation(@PathVariable Long id) {
        educationService.delete(id);
        return new ResponseEntity<>("This Education with Id: "+id +" have been deleted", HttpStatus.OK);
    }
    @GetMapping("/educationById/{Id}")
    public BaseApi<?> educationById(@PathVariable Long Id) {
        Education education = educationService.getEducationById(Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Education have been found")
                .timestamp(LocalDateTime.now())
                .data(education)
                .build();
    }
}
