package com.cats.payrollservice.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class PayslipReqDto {
    private Double khmerRate = 4000.0;
    private List<Long> emId;
}
