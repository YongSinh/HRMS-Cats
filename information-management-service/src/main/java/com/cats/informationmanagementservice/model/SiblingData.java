package com.cats.informationmanagementservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "siblings_data")
@Getter
@Setter
public class SiblingData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "empId")
    private Long empId;
    @Column(name ="firstName" )
    private String firstName;
    @Column(name ="lastName" )
    private String lastName;
    @Column(name ="age" )
    private Long age;
    @Column(name ="sex" )
    private String sex;
    @Column(name ="education" )
    private String education;
    @Column(name ="occupation" )
    private String occupation;
    @Column(name ="position" )
    private String position;
    @Column(name ="office" )
    private String office;
}
