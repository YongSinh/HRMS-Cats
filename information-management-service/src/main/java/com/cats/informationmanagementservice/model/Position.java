package com.cats.informationmanagementservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "positionid")
    private Long posId;
    @Column(name = "positionName")
    private String posName;
    @Column(name = "department_id")
    private Long DepId;
}
