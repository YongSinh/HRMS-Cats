package com.cats.payrollservice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OverTime {
    private Long overTimeId;
    private Long emId;
    private LocalDate overTimeDate;
    private Integer overtimeHour;
    private Integer rate;
}
