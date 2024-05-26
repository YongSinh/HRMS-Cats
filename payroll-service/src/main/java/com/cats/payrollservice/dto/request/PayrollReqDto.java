package com.cats.payrollservice.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayrollReqDto {
    private List<Long> empIds;
    private Long empId;
    private String refNo;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Integer type;
    private Integer status;
    private LocalDate dateCreate;
    private Double khmerRate;
}
