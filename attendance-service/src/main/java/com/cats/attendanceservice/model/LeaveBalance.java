package com.cats.attendanceservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        @Column(name = "leave_type_id")
        private Long leaveTypeId;
        @Column(name = "balance_amount")
        private Long balanceAmount;
        @Column(name = "last_update_date")
        private Long lastUpdateDate;
}
