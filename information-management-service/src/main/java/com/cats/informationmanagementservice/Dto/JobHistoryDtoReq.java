package com.cats.informationmanagementservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobHistoryDtoReq {
    private LocalDate startDate;
    private LocalDate endDate;
    private String jobTitle;
    private String department;
    private Long empId;
}
