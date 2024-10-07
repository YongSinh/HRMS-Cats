package com.cats.attendanceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Setter
@Getter
@AllArgsConstructor
public class TimeOutReqDto {
    private Long emId;
    private LocalTime timeOut;
    private LocalDate dateOut;
    private String remark;
}
