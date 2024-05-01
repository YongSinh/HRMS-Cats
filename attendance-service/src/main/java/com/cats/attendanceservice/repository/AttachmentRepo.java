package com.cats.attendanceservice.repository;

import com.cats.attendanceservice.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, String> {

    List<Attachment> findByEmId(Long emId);
}
