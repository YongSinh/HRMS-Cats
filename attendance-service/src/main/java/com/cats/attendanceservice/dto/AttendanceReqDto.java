package com.cats.attendanceservice.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Setter
@Getter
@AllArgsConstructor
public class AttendanceReqDto {
    private Long id;
    private Long emId;
    private LocalTime timeIn;
    private LocalTime timeOut;
    private LocalDate DateIn;
    private String remark;
}
