package com.cats.attendanceservice.service;

import com.cats.attendanceservice.model.FileInfo;
import com.cats.attendanceservice.repository.AttachmentRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileServiceImp implements FileService {

    private final AttachmentRepo attachmentRepo;
    @Value("${filePath}")
    private String basePath;
    @Override
    public void store(MultipartFile file, Long emId, Integer type, LocalDate dateCreated, Integer serviceType) throws IOException {
        FileInfo fileInfo = new FileInfo();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        fileInfo.setType(type);
        fileInfo.setFileType(file.getContentType());
        fileInfo.setFileName(fileName);
        fileInfo.setFileSize(file.getSize());
        fileInfo.setEmId(emId);
        fileInfo.setServiceType(serviceType);
        fileInfo.setDateCreated(dateCreated);
        uploadFileDir(file);
        attachmentRepo.save(fileInfo);
    }

    @Override
    public void updateStore(MultipartFile file, Long emId, Integer type, LocalDate dateCreated, Integer serviceType, String fileId) throws IOException {
        FileInfo fileInfo = getFile(fileId);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        fileInfo.setType(type);
        fileInfo.setFileType(file.getContentType());
        fileInfo.setFileName(fileName);
        fileInfo.setFileSize(file.getSize());
        fileInfo.setEmId(emId);
        fileInfo.setServiceType(serviceType);
        fileInfo.setDateCreated(dateCreated);
        uploadFileDir(file);
        attachmentRepo.save(fileInfo);
    }


    @Override
    public void store2(MultipartFile file) throws IOException {
        FileInfo fileInfo = new FileInfo();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        fileInfo.setType(1);
        fileInfo.setFileType(file.getContentType());
        fileInfo.setFileName(fileName);
        //fileInfo.setFile(file.getBytes());
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
    public Stream<FileInfo> getListFileByEmIdWithServiceType(Long emId, Integer type, Integer service) {
        return attachmentRepo.findAllByEmIdAndTypeAndServiceType(emId, type, service).stream();
    }

    @Override
    public Stream<FileInfo> getListFileByEmIdAndType(Long emId, Integer type) {
        return attachmentRepo.findByEmIdAndType(emId,type).stream();
    }

    @Override
    public Stream<FileInfo> getListFileByEmIdAndTypeAndDate(LocalDate date, Long emId, Integer type) {
        return attachmentRepo.findByDateCreatedAndEmIdAndType(date,emId,type).stream();
    }

    @Override
    public Resource downloadFile(String fileName) {
        try {
            // Construct the file path
            File dir = new File(basePath + fileName);
            Path filePath = Path.of(basePath).resolve(fileName).normalize();  // Ensure safe path
            File file = filePath.toFile();

            // Check if the file exists and is readable
            if (file.exists() && file.canRead()) {
                return new UrlResource(file.toURI());
            } else {
                throw new FileNotFoundException("File not found or not readable: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String uploadFileDir(MultipartFile file) {
        File dir = new File(basePath+ file.getOriginalFilename());

        if (dir.exists()) {
            return "EXIST";
        }

//        if (!dir.exists()) {
//            dir.mkdirs();  // Create the directory if it doesn't exist
//        }
        // Get the original filename
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            return "INVALID_FILE";
        }
        Path path = Path.of(basePath + file.getOriginalFilename());

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return "CREATED";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "FAILED";
    }

    @Override
    public Stream<FileInfo> getListFileByEmIdAndTypeServiceAndDate(LocalDate date, Long emId, Integer type, Integer service) {
        return attachmentRepo.findAllByDateCreatedAndEmIdAndTypeAndServiceTypeOrderByDateCreatedDesc(date,emId,type,service).stream();
    }
}
