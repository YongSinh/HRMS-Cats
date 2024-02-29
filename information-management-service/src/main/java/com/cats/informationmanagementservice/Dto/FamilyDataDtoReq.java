package com.cats.informationmanagementservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyDataDtoReq {
    private String fatherName;
    private String fatherAddress;
    private String fatherOccupation;
    private String fatherPhoneNum;
    private String motherName;
    private String motherAddress;
    private String motherOccupation;
    private String motherPhoneNum;
    private String siblings;
    private String maritalStats;
    private Long emId;
}
