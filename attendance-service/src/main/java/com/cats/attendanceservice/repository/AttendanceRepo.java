package com.cats.attendanceservice.repository;

import com.cats.attendanceservice.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {
    @Query(value = "select * from attendance order by DateIn", nativeQuery = true)
    List<Attendance> findAllOrderByDateIn();
    @Query(value = "select * from attendance where emId = ?1 order by DateIn", nativeQuery = true)
    List<Attendance> findByEmId(Long emId);
    @Query(value = "select * from attendance\n" +
            "where dateIn = :date and emId = :emId", nativeQuery = true)
    Attendance findByEmIdAndDateIn(@Param("date") LocalDate date, @Param("emId") Long emId);
    List<Attendance> findByEmIdIn(Collection<Long> emId);
    List<Attendance> findByDateInBetweenAndEmId(LocalDate dateIn, LocalDate dateIn2, Long emId);
    //@Query("SELECT a FROM Attendance a WHERE a.emId = :emId ORDER BY a.timeIn DESC")
    Optional<Attendance> findLastTimeInByEmId(@Param("emId") Long emId);

    //@Query("SELECT a FROM Attendance a WHERE a.emId = :emId ORDER BY a.timeOut DESC")
    Optional<Attendance> findLastTimeOutByEmId(@Param("emId") Long emId);
}
