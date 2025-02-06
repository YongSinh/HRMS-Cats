package com.cats.informationmanagementservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "siblings_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SiblingData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "age")
    private Long age;
    @Column(name = "sex")
    private String sex;
    @Column(name = "education")
    private String education;
    @Column(name = "occupation")
    private String occupation;
    @Column(name = "position")
    private String position;
    @Column(name = "office")
    private String office;
    @Column(name = "marital_stats")
    private String maritalStats;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empId")
    @JsonBackReference
    private Employee employee;

}
