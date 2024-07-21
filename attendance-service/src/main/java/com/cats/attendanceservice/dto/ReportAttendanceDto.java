package com.cats.attendanceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportAttendanceDto {
    private Long emId;
    private String name;
    private String dateIn;
    private String timeIn;
    private String dateOut;
    private String timeOut;
    private String remark;
}
