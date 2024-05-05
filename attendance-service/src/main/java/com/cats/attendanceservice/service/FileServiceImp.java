package com.cats.attendanceservice.service;

import com.cats.attendanceservice.model.FileInfo;
import com.cats.attendanceservice.repository.AttachmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService {

    private final AttachmentRepo attachmentRepo;

    @Override
    public void store(MultipartFile file, Long emId, Integer type, LocalDate dateCreated) throws IOException {
        FileInfo fileInfo = new FileInfo();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        fileInfo.setType(type);
        fileInfo.setFileType(file.getContentType());
        fileInfo.setFileName(fileName);
        fileInfo.setFile(file.getBytes());
        fileInfo.setEmId(emId);
        fileInfo.setDateCreated(dateCreated);
        System.out.println("Hello");
        attachmentRepo.save(fileInfo);
    }



    @Override
    public void store2(MultipartFile file) throws IOException {
        FileInfo fileInfo = new FileInfo();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        fileInfo.setType(1);
        fileInfo.setFileType(file.getContentType());
        fileInfo.setFileName(fileName);
        fileInfo.setFile(file.getBytes());
        fileInfo.setEmId(2431L);
        attachmentRepo.save(fileInfo);
    }

    @Override
    public FileInfo getFile(String id) {
        return attachmentRepo.findById(id).get();
    }

    @Override
    public Stream<FileInfo> getAllFileByEmId(Long emId) {
        return attachmentRepo.findByEmId(emId).stream();
    }

    @Override
    public Stream<FileInfo> getAllFile() {
        return attachmentRepo.findAll().stream();
    }

    @Override
    public Stream<FileInfo> getListFileByEmIdAndType(Long emId, Integer type) {
        return attachmentRepo.findByEmIdAndType(emId,type).stream();
    }

    @Override
    public Stream<FileInfo> getListFileByEmIdAndTypeAndDate(LocalDate date, Long emId, Integer type) {
        return attachmentRepo.findByDateCreatedAndEmIdAndType(date,emId,type).stream();
    }
}
