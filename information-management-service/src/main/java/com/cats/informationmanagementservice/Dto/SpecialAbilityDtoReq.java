package com.cats.informationmanagementservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialAbilityDtoReq {
    private String foreignLanguages;
    private String speaking;
    private String listening;
    private String writing;
    private String reading;
    private Long empId;
}
