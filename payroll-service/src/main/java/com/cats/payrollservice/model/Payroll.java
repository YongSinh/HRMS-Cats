package com.cats.payrollservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "payroll")
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Payroll {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="id")
    private Long Id;
    @Column(name ="empId")
    private Long empId;
    @Column(name ="ref_no", columnDefinition = "varchar(50)")
    private String refNo;
    @Column(name ="date_from")
    private LocalDate dateFrom;
    @Column(name ="date_to")
    private LocalDate dateTo;
    @Column(name ="type")
    private Integer type;
    @Column(name ="status")
    private Integer status;
    @Column(name ="date_created")
    private LocalDate dateCreate;
    @JsonManagedReference
    @OneToMany(mappedBy = "payroll", cascade = {CascadeType.REMOVE }, fetch = FetchType.EAGER)
    private List<Payslip> payslips = new ArrayList<>();
}
