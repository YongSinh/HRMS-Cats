package com.cats.informationmanagementservice.Dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDtoRep {
    private Long Id;
    private String depFullName;
    private String depName;
}
