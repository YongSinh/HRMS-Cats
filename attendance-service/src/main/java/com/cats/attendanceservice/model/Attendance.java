package com.cats.attendanceservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Table(name = "attendance")
@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "emId")
    private Long emId;
    @Column(name = "timeInDate")
    private LocalTime timeIn;
    @Column(name = "timeOutDate")
    private LocalTime timeOut;
    @Column(name = "dateIn")
    private LocalDate dateIn;
    @Column(name = "dateOut")
    private LocalDate dateOut;
    @Column(name = "remark", columnDefinition = "varchar(150)")
    private String remark;

}
