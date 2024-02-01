package com.cats.informationmanagementservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "special_ability")
@Getter
@Setter
public class SpecialAbility {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "empId")
    private Long empId;
    @Column(name = "foreign_langauges")
    private String foreignLanguages;
    @Column(name = "speaking")
    private String speaking;
    @Column(name = "listening")
    private String listening;
    @Column(name = "writing")
    private String writing;
}
