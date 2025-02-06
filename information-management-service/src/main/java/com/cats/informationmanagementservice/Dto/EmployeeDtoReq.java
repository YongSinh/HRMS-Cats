package com.cats.informationmanagementservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeDtoReq {
    private Long empId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String placeOfBirth;
    private Long age;
    private String sex;
    private Double height;
    private Double Weight;
    private String address;
    private LocalDate empDate;
    private LocalDate joinDate;
    private Long mangerId;
    private String location;
    private String maritalStats;
    private String nationality;
    private String workType;
    private String religion;
    private String idCard;
    private String passport;
    private String remark;
    private String govOfficer;
    private String govTel;
    private String govAddress;
    private String govPosition;
    private Long depId;
    private String posId;

}
