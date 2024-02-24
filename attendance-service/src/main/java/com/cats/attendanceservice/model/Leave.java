package com.cats.attendanceservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Table (name = "leave")
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Leave {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long leaveId;
    @Column(name = "empid")
    private Long empId;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "time")
    private LocalTime timeOfHaftDay;
    @Column(name = "reason")
    private String reason;
    @Column(name = "leaveTypeId" )
    private Integer leaveTypeId;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "approved")
    private Boolean approved;
    @Column(name = "approvedByManger")
    private Boolean approvedByManger;
    @Column(name = "approvedByHead")
    private Boolean approvedByHead;
    @Column(name = "approvedByHr")
    private Boolean approvedByHr;
    @Column(name = "remark")
    private String remark;
    @Column(name = "day" )
    private Integer dayOfLeave;
    @Column(name = "created_at")
    private LocalDateTime createdAt;



}
