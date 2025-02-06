package com.cats.informationmanagementservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SiblingDataDtoReq {
    private Long empId;
    private String firstName;
    private String lastName;
    private Long age;
    private String sex;
    private String education;
    private String occupation;
    private String position;
    private String office;
    private String maritalStats;
}
