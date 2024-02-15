package com.cats.informationmanagementservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "education")
public class Education {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "empid")
    private Long empId;
    @Column(name = "education_level")
    private String  eduLevel;
    @Column(name = "education_institution")
    private String  eduInstitution;
    @Column(name = "year_end")
    private LocalDate yearEnd;
    @Column(name = "major")
    private String  major;
    @Column(name = "GPA")
    private Double  GPA;

}
