package com.cats.attendanceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LeaveBalanceListDtoReq {
        private List<Long> empId;
        private Long balanceAmount;
        private LocalDateTime lastUpdateDate;
        private List<String> leaveType;
}
