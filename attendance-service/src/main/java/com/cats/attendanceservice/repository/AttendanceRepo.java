package com.cats.attendanceservice.repository;

import com.cats.attendanceservice.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {
    @Query(value = "select * from attendance order by DateIn", nativeQuery = true)
    List<Attendance> findAllOrderByDateIn();
    List<Attendance> findByEmId(Long emId);
}
