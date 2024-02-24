package com.cats.informationmanagementservice.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDtoReq {
    private Long Id;
    private String depName;
}
