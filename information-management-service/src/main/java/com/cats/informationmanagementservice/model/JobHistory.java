package com.cats.informationmanagementservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "job_history")
public class JobHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "jobTitle")
    private String jobTitle;
    @Column(name = "department")
    private String department;

    @ManyToOne( fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emid")
    @JsonBackReference
    private Employee employee;
}
