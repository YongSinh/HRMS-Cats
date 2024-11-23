package com.cats.attendanceservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "leave_balance")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LeaveBalance  {
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        @Column(name = "id")
        private Long id;
        @Column(name = "empid")
        private Long empId;
        @Column(name = "balance_amount")
        private Long balanceAmount;
        @Column(name = "last_update_date")
        private LocalDateTime lastUpdateDate;
        @ManyToOne( fetch = FetchType.EAGER, optional = false)
        @JsonBackReference
        @JoinColumn(name = "leave_type_id")
        private LeaveType leaveType;
}
