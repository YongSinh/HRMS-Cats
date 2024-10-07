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
//    private Double allowanceAmount;
//    private String allowances;
//    private Double deductionAmount;
//    private String deductions;
    private LocalDateTime dateCreated;
    private Double khmerRate;
    private LocalDate payrollDate;
    private Integer paymentType;
    private List<Long> emId;
}
