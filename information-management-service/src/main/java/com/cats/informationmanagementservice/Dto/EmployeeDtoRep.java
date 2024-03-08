package com.cats.informationmanagementservice.Dto;

import com.cats.informationmanagementservice.model.EmergencyContact;
import com.cats.informationmanagementservice.model.JobHistory;
import com.cats.informationmanagementservice.model.SiblingData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EmployeeDtoRep {
    private Long empId;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private Long age;
    private String sex;
    private Double height;
    private Double Weight;
    private String address;
    private LocalDate empDate;
    private LocalDate joinDate;
    private Long mangerId;
    private String  location;
    private String maritalStats;
    private String  nationality;
    private String workType;
    private String  religion;
    private String  idCard;
    private String  passport;
    private String  remark;
    private String  govOfficer;
    private String  govTel;
    private String  govAddress;
    private String  govPosition;
    private String depId;
    private String posId;
    private List<EducationDtoRep> educationDtoReps;
    private  List<JobHistory> jobHistoryDtoReps;
    private List<EmergencyContact> emergencyContacts;
    private List<SiblingData> siblingData;

}
