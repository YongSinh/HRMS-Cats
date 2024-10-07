package com.cats.attendanceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Setter
@Getter
@AllArgsConstructor
public class TimeInReqDto {
    private Long emId;
    private LocalTime timeIn;
    private LocalDate dateIn;
    private String remark;
}
