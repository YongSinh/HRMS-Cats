package com.cats.informationmanagementservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "family_data")
@NoArgsConstructor
@AllArgsConstructor
public class  FamilyData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "father_name")
    private String fatherName;
    @Column(name = "father_address")
    private String fatherAddress;
    @Column(name = "father_occupation")
    private String fatherOccupation;
    @Column(name = "father_phoneNum")
    private String fatherPhoneNum;
    @Column(name = "mother_name")
    private String motherName;
    @Column(name = "mother_address")
    private String motherAddress;
    @Column(name = "mother_occupation")
    private String motherOccupation;
    @Column(name = "mother_phoneNum")
    private String motherPhoneNum;
    @OneToOne ( fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empid")
    @JsonBackReference
    private Employee employee;
}
