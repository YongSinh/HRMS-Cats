package com.cats.informationmanagementservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "position")
@NoArgsConstructor
public class Position {
    @Id
    @Column(name = "positionid")
    private String posId;
    @Column(name = "positionName")
    private String posName;
    @Column(name = "poSection")
    private String poSection;
    @Column(name = "poLevel")
    private String poLevel;

    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();

}
