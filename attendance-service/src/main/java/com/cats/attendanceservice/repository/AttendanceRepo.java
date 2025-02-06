package com.cats.attendanceservice.repository;

import com.cats.attendanceservice.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {
    @Query(value = "select * from attendance order by DateIn DESC ", nativeQuery = true)
    List<Attendance> findAllOrderByDateIn();

    List<Attendance> findAllByOrderByIdDesc();

    @Query(value = "select * from attendance where emId = ?1 order by DateIn Desc ", nativeQuery = true)
    List<Attendance> findByEmId(Long emId);

    @Query(value = "select * from attendance\n" +
            "where dateIn = :date and emId = :emId", nativeQuery = true)
    Attendance findByEmIdAndDateIn(@Param("date") LocalDate date, @Param("emId") Long emId);

    @Query(
            value = "SELECT * FROM attendance WHERE attendance.emId IN :emIds ORDER BY dateIn DESC, timeInDate DESC",
            nativeQuery = true
    )
    List<Attendance> findByEmIdIn(Collection<Long> emIds);

    List<Attendance> findByDateInBetweenAndEmId(LocalDate dateIn, LocalDate dateIn2, Long emId);

    @Query("SELECT a FROM Attendance a WHERE a.emId = :emId AND a.dateIn = :dateIn")
    Optional<Attendance> findByEmIdAndDateIn(@Param("emId") Long emId, @Param("dateIn") LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.emId = :emId AND a.dateIn = :dateIn")
    Optional<Attendance> findLastTimeInByEmId(@Param("emId") Long emId, @Param("dateIn") LocalDate dateIn);

    @Query("SELECT a FROM Attendance a WHERE a.emId = :emId AND a.dateIn = :dateIn")
    List<Attendance> preventDuplicates(@Param("emId") Long emId, @Param("dateIn") LocalDate date);

    @Modifying
    @Transactional
    @Query("DELETE FROM Attendance a WHERE a.emId = :emId AND a.dateIn = :dateIn")
    void deleteDuplicates(@Param("emId") Long emId, @Param("dateIn") LocalDate date);

    @Query("SELECT a FROM Attendance a WHERE a.emId = :emId  AND a.dateIn = :dateIn")
    Optional<Attendance> findLastTimeOutByEmId(@Param("emId") Long emId, @Param("dateIn") LocalDate dateIn);
}
