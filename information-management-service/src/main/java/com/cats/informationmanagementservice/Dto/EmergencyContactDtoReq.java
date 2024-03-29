package com.cats.informationmanagementservice.Dto;

import com.cats.informationmanagementservice.model.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContactDtoReq {
    private Long id;
    private String fullName;
    private String relationship;
    private String address;
    private String tel;
    private String officeAddress;
    private String officeTel;
    private Long empId;

}
