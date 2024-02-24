package com.cats.attendanceservice.model;

import jakarta.persistence.*;

@Table(name = "attendance")
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
}
