package com.cats.informationmanagementservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name ="empid" )
    private Long empId;
    @Column(name ="employee_code" )
    private String empCode;
    @Column(name ="firstName" )
    private String firstName;
    @Column(name ="lastName" )
    private String lastName;
    @Column(name ="email" )
    private String email;
    @Column(name ="positionid")
    private Integer positionId;
    @Column(name ="department_id")
    private Integer departmentId;
    @Column(name ="birthDate")
    private LocalDate birthDate;
    @Column(name ="age")
    private Integer age;
    @Column(name ="sex")
    private String sex;
    @Column(name ="Height")
    private Double height;
    @Column(name ="Weight")
    private Double Weight;
    @Column(name ="address")
    private String address;
    @Column(name ="empDate")
    private LocalDate empDate;
    @Column(name ="manager_id")
    private Integer mangerId;
    @Column(name ="location")
    private String  location;
    @Column(name ="marital_stats")
    private String maritalStats;
    @Column(name ="nationality")
    private String  nationality;
    @Column(name ="id_card")
    private String  idCard;
    @Column(name ="passport_id")
    private String  passport;
    @Column(name ="remark")
    private String  remark;
}
