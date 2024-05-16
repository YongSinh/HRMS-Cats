package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.Dto.EmergencyContactDtoReq;
import com.cats.informationmanagementservice.base.BaseApi;
import com.cats.informationmanagementservice.model.EmergencyContact;
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


    private final EmergencyService emergencyService;
    @PostMapping("/add")
    public BaseApi<?> addEmergencyContact(@RequestBody EmergencyContactDtoReq emergencyContactDtoReq) {

        EmergencyContact emergencyContact = emergencyService.create(emergencyContactDtoReq);
      return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Emergency Contact have been created")
                .timestamp(LocalDateTime.now())
                .data(emergencyContact)
                .build();
    }

    @GetMapping("/getEmergencyContactByEmId")
    public BaseApi<?> getEmergencyContactByEmId(@RequestParam Long emId) {
        List<EmergencyContact> emergencyContacts = emergencyService.getListEmergencyContactByEmId(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Emergency Contact have been found")
                .timestamp(LocalDateTime.now())
                .data(emergencyContacts)
                .build();
    }
    @PutMapping("/edit")
    public BaseApi<?> editJobHistory(@RequestBody EmergencyContactDtoReq emergencyContactDtoReq, @RequestParam Long Id) {
        EmergencyContact emergencyContact = emergencyService.edit(emergencyContactDtoReq, Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Emergency Contact have been updated")
                .timestamp(LocalDateTime.now())
                .data(emergencyContact)
                .build();
    }

    @GetMapping("/getListEmergencyContact")
    public BaseApi<?> getListEmergencyContact() {
        List<EmergencyContact> emergencyContacts = emergencyService.getList();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Emergency Contact have been found")
                .timestamp(LocalDateTime.now())
                .data(emergencyContacts)
                .build();
    }
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<?> deleteEmergencyContact(@RequestParam Long id) {
        emergencyService.delete(id);
        return new ResponseEntity<>("This Emergency Contact  with Id: "+id +" have been deleted", HttpStatus.OK);
    }
    @GetMapping("/getEmergencyContactById")
    public BaseApi<?> getEmergencyContactById(@RequestParam Long Id) {
        EmergencyContact emergencyContact = emergencyService.getEmergencyContactById(Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Emergency Contact have been found")
                .timestamp(LocalDateTime.now())
                .data(emergencyContact)
                .build();
    }
}
