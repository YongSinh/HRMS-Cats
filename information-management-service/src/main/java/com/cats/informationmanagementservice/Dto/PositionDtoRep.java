package com.cats.informationmanagementservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDtoRep {
    private Long Id;
    private String posName;
    private String depName;
}
