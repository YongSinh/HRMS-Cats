package com.cats.informationmanagementservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name ="empid" )
    private Long empId;
    @Column(name ="firstName" )
    private String firstName;
    @Column(name ="lastName" )
    private String lastName;
    @Column(name ="email" )
    private String email;
    @Column(name ="birthDate")
    private LocalDate birthDate;
    @Column(name ="age")
    private Long age;
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
    @Column(name ="joingDate")
    private LocalDate joinDate;
    @Column(name ="manager_id")
    private Long mangerId;
    @Column(name ="location")
    private String  location;
    @Column(name ="marital_stats")
    private String maritalStats;
    @Column(name ="nationality")
    private String  nationality;
    @Column(name ="workType")
    private String workType;
    @Column(name ="religion")
    private String  religion;
    @Column(name ="id_card")
    private String  idCard;
    @Column(name ="passport_id")
    private String  passport;
    @Column(name ="remark")
    private String  remark;
    @Column(name ="gov_Officer")
    private String  govOfficer;
    @Column(name ="gov_tel")
    private String  govTel;
    @Column(name ="gov_officer_address")
    private String  govAddress;
    @Column(name ="gov_postion")
    private String  govPosition;
    @OnDelete(action = OnDeleteAction.CASCADE)

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY , optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "positionid")
    private Position position;

    @JsonManagedReference
    @OneToMany(mappedBy = "employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<JobHistory> jobHistories = new ArrayList<>();

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<Education> educations = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<EmergencyContact> emergencyContacts = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<SiblingData> siblingData = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<SpecialAbility> specialAbilities = new ArrayList<>();
}
