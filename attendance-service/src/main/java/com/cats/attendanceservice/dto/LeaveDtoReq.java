package com.cats.attendanceservice.dto;

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
@RequiredArgsConstructor
public class LeaveDtoReq {
    private Long empId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime timeOfHaftDay;
    private String reason;
    private String leaveTypeId;
    private Boolean status;
    private Boolean approved;
    private Boolean cancelled;
    private Boolean approvedByManger;
    private Boolean approvedByHead;
    private Boolean approvedByHr;
    private String remark;
    private Integer dayOfLeave;
    private LocalDate createdAt;
}
