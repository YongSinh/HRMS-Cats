package com.cats.attendanceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class LeaveDtoRep {
    private Long Id;
    private Long empId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalTime timeOfHaftDay;
    private String reason;
    private String leaveType;
    private Boolean status;
    private Boolean cancelled;
    private Boolean approved;
    private Boolean approvedByManger;
    private Boolean approvedByHead;
    private Boolean approvedByHr;
    private String remark;
    private Integer dayOfLeave;
    private LocalDateTime createdAt;
}
