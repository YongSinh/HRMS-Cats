package com.cats.attendanceservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRepDto {
    private Long id;
    private Long emId;
    private String employeeName;
    private LocalTime timeIn;
    private LocalTime timeOut;
    private LocalDate dateIn;
    private LocalDate dateOut;
    private String remark;
    private Boolean onLeave;
}
