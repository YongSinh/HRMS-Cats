package com.cats.attendanceservice.repository;

import com.cats.attendanceservice.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttachmentRepo extends JpaRepository<FileInfo, String> {

    List<FileInfo> findByEmId(Long emId);
    List<FileInfo> findAllByEmIdAndTypeAndServiceType(Long emId, Integer type, Integer serviceType);
    List<FileInfo> findAllByDateCreatedAndEmIdAndTypeAndServiceType(LocalDate dateCreated, Long emId, Integer type, Integer serviceType);
    List<FileInfo> findByEmIdAndType(Long emId, Integer type);
//    @Query(nativeQuery = true, value = "select * from cats_hrms.attachment where dateCreated =:date " +
//            "and empid =:emId and type=:type")
//    List<FileInfo> findByDateCreatedAndEmIdAndType(@Param("date") LocalDate date, @Param("emId") Long emId, @Param("type")Integer type);

    List<FileInfo> findByDateCreatedAndEmIdAndType(LocalDate date, Long emId, Integer type);
}
