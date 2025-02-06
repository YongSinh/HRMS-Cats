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
public class JobHistoryDtoRep {
    private Long Id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String jobTitle;
    private String department;
}
