package com.cats.payrollservice.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeAllowancesDto5 {
    private Long allowanceId;
    private Integer type;
    private Double amount;
    private List<Long> emId; // List of employee IDs
}
