package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.RequestFile;
import com.cats.attendanceservice.model.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;


public interface FileService {

    void store(MultipartFile file, Long emId, Integer type) throws IOException;

    void store2(MultipartFile file) throws IOException;
    Attachment getFile(String id);
    Stream<Attachment> getAllFileByEmId(Long emId);
    Stream<Attachment> getAllFile();
}
