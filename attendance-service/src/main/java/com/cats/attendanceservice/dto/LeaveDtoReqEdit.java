package com.cats.attendanceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class LeaveDtoReqEdit {
    private Long leaveId;
    private String fileId;
    private Long empId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime timeOfHaftDay;
    private String reason;
    private String leaveTypeId;
    private String remark;
    private Integer dayOfLeave;
    private LocalDate createdAt;
}
