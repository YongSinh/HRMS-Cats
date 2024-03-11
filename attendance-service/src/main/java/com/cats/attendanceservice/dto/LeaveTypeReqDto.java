package com.cats.attendanceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LeaveTypeReqDto {
    private String id;
    private String leaveTitle;
    private String leaveDes;
    private Long leaveDayPerYear;

}
