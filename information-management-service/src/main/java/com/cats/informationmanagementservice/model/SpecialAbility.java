package com.cats.informationmanagementservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "special_ability")
@Getter
@Setter
public class    SpecialAbility {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "foreign_langauges")
    private String foreignLanguages;
    @Column(name = "speaking")
    private String speaking;
    @Column(name = "listening")
    private String listening;
    @Column(name = "writing")
    private String writing;
    @Column(name = "reading")
    private String reading;
    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empId")
    @JsonBackReference
    private Employee employee;
}
