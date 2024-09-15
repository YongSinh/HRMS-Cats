package com.cats.informationmanagementservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInfo {
    private Long emId;
    private String fullName;
    private String section;
    private String department;
    private String position;
    private String email;
    private String location;
}
