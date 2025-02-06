package com.cats.informationmanagementservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContactDtoReq {
    private String fullName;
    private String relationship;
    private String address;
    private String tel;
    private String officeAddress;
    private String officeTel;
    private Long empId;

}
