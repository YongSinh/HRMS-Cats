package com.cats.attendanceservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private LocalDateTime timeIn;
    @Column(name = "timeOutDate")
    private LocalDateTime timeOut;
    @Column(name = "remark", columnDefinition = "varchar(150)")
    private String remark;

}
