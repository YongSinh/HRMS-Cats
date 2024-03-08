package com.cats.attendanceservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "leave_type")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LeaveType {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "leave_title")
    private String leaveTitle;
    @Column(name = "leave_des")
    private String leaveDes;
    @Column(name = "leave_day_per_year")
    private Long leaveDayPerYear;

}
