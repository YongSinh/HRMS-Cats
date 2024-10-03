package com.cats.attendanceservice.service;

import com.cats.attendanceservice.model.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Stream;


public interface FileService {
    void store(MultipartFile file, Long emId, Integer type, LocalDate dateCreated, Integer serviceType) throws IOException;
    void updateStore(MultipartFile file, Long emId, Integer type, LocalDate dateCreated, Integer serviceType, String fileId ) throws IOException;
    void store2(MultipartFile file) throws IOException;
    FileInfo getFile(String id);
    Stream<FileInfo> getAllFileByEmId(Long emId);
    Stream<FileInfo> getAllFile();
    Stream<FileInfo> getListFileByEmIdWithServiceType(Long emId, Integer type, Integer service);
    Stream<FileInfo> getListFileByEmIdAndType(Long emId, Integer type);
    Stream<FileInfo> getListFileByEmIdAndTypeAndDate(LocalDate date,Long emId, Integer type);
    Resource downloadFile(String fileName);
    String uploadFileDir(MultipartFile file);
    Stream<FileInfo> getListFileByEmIdAndTypeServiceAndDate(LocalDate date,Long emId, Integer type, Integer service);
}
