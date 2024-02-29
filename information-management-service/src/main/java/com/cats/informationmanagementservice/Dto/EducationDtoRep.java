package com.cats.informationmanagementservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EducationDtoRep {
    private Long id;
    private String  eduLevel;
    private String  eduInstitution;
    private LocalDate yearEnd;
    private String  major;
    private Double  GPA;
}
