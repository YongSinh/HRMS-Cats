package com.cats.attendanceservice.dto;

import com.cats.attendanceservice.model.LeaveType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LeaveBalanceDtoReq {
        private Long empId;
        private Long balanceAmount;
        private LocalDateTime lastUpdateDate;
        private String leaveType;
}
