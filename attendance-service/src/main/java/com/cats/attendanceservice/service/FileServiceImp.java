package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.RequestFile;
import com.cats.attendanceservice.model.Attachment;
import com.cats.attendanceservice.repository.AttachmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService {

    private final AttachmentRepo attachmentRepo;

    @Override
    public void store(MultipartFile file, Long emId, Integer type) throws IOException {
        Attachment attachment = new Attachment();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        attachment.setType(type);
        attachment.setFileType(file.getContentType());
        attachment.setFileName(fileName);
        attachment.setFile(file.getBytes());
        attachment.setEmId(emId);
        System.out.println("Hello");
        attachmentRepo.save(attachment);
    }



    @Override
    public void store2(MultipartFile file) throws IOException {
        Attachment attachment = new Attachment();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        attachment.setType(1);
        attachment.setFileType(file.getContentType());
        attachment.setFileName(fileName);
        attachment.setFile(file.getBytes());
        attachment.setEmId(2431L);
        System.out.println("Hello");
        attachmentRepo.save(attachment);
    }

    @Override
    public Attachment getFile(String id) {
        return attachmentRepo.findById(id).get();
    }

    @Override
    public Stream<Attachment> getAllFile() {
        return attachmentRepo.findAll().stream();
    }
}
