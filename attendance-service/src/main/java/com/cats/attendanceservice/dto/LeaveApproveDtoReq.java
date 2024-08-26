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
public class LeaveApproveDtoReq {
    private Boolean approved;
    private Boolean cancelled;
    private Boolean approvedByManger;
    private Boolean approvedByHead;
    private Boolean approvedByHr;
    private String remark;
    private Integer dayOfLeave;
    private LocalDate createdAt;
}
