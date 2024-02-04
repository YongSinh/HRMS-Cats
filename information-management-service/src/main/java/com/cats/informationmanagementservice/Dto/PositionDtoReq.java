package com.cats.informationmanagementservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PositionDtoReq {
    private String posName;
    private Long depId;
}
