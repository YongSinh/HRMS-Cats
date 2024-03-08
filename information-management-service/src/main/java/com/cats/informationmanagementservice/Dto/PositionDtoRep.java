package com.cats.informationmanagementservice.Dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionDtoRep {
    private String Id;
    private String posName;
    private String depName;
    private String poSection;
    private String poLevel;
}
