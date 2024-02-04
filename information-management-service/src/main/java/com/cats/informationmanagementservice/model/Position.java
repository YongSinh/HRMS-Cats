package com.cats.informationmanagementservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
