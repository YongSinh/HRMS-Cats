package com.cats.informationmanagementservice.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDtoReq {
    private String depName;
    private String depFullName;
}
