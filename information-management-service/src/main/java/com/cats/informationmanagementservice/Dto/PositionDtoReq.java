package com.cats.informationmanagementservice.Dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDtoReq {
    private String poId;
    private String posName;
    private Long depId;
    private String poSection;
    private String poLevel;
}
