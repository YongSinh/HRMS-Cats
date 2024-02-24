package com.cats.informationmanagementservice.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "family_data")
public class FamilyData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "empid")
    private Long empId;
    @Column(name = "father_name")
    private String fatherName;
    @Column(name = "father_address")
    private String fatherAddress;
    @Column(name = "father_occupation")
    private String fatherOccupation;
    @Column(name = "mother_name")
    private String motherName;
    @Column(name = "mother_address")
    private String motherAddress;
    @Column(name = "mother_occupation")
    private String motherOccupation;
    @Column(name = "siblings")
    private String siblings;
    @Column(name = "marital_stats")
    private String maritalStats;
}
