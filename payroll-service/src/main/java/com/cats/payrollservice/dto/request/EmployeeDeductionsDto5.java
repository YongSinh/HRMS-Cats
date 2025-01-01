package com.cats.payrollservice.dto.request;

import lombok.Data;

import java.util.List;
@Data
public class EmployeeDeductionsDto5 {
    private Long deductionsId;
    private Integer type;
    private Double amount;
    private List<Long> emId; // List of employee IDs
}
