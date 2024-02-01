package com.cats.informationmanagementservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name ="emergency_contact" )
public class EmergencyContact {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "empid")
    private Long empId;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "relationship")
    private String relationship;
    @Column(name = "address")
    private String address;
    @Column(name = "tel")
    private String tel;
    @Column(name = "office_address")
    private String officeAddress;
    @Column(name = "office_tel")
    private String officeTel;
}
