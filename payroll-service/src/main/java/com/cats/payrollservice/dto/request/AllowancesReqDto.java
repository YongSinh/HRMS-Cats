package com.cats.payrollservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class AllowancesReqDto {
    private String allowances;
    private String description;
}
