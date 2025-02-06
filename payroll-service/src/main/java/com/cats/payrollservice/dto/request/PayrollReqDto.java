package com.cats.payrollservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayrollReqDto {
    private List<Long> empIds;
    private Integer status;
}
