package com.cats.attendanceservice.controller;

import com.cats.attendanceservice.dto.RequestFile;
import com.cats.attendanceservice.dto.ResponseFile;
import com.cats.attendanceservice.model.FileInfo;
import com.cats.attendanceservice.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    public static String convertToString(List<String> list) {
        return list.stream()
                .map(String::trim) // Trim each email address
                .collect(Collectors.joining(", "));
    }
    @RequestMapping(value = "/files/upload", method = RequestMethod.POST, consumes = { "multipart/form-data"})
    public ResponseEntity<?> uploadFile(@RequestPart("file") @Valid MultipartFile file, @RequestPart("body")  @Valid RequestFile requestFile){
        try {
            fileService.store(file,requestFile.getEmId(), requestFile.getType(), requestFile.getDateCreated());
            String message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
            String message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @RequestMapping(value = "/files/uploadMultiple", method = RequestMethod.POST, consumes = { "multipart/form-data"})
    public ResponseEntity<?> uploadFileMultipart(@RequestPart("file") @Valid MultipartFile[] files, @RequestPart("body")  @Valid RequestFile requestFile){
        List<String> fileNames = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                fileService.store(file,requestFile.getEmId(), requestFile.getType(), requestFile.getDateCreated());
                fileNames.add(fileName);
            }
            String message = "Uploaded the files successfully: " +  convertToString(fileNames);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }catch (Exception e){
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                fileNames.add(fileName);
            }
            String message = "Could not upload the files: " +  convertToString(fileNames) + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileInfo fileDB = fileService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFileName() + "\"")
                .body(fileDB.getFile());
    }
    @GetMapping("/files")
    public ResponseEntity<?> getListFiles() {
        List<ResponseFile> files = fileService.getAllFile().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/")
                    .path(dbFile.getFileId())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getFileName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getFile().length,
                    dbFile.getEmId(),
                    dbFile.getFileType(),
                    dbFile.getDateCreated()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/ByEmId")
    public ResponseEntity<?> getListFilesByEmId(@RequestParam("emId")  Long emId) {
        List<ResponseFile> files = fileService.getAllFileByEmId(emId).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/")
                    .path(dbFile.getFileId())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getFileName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getFile().length,
                    dbFile.getEmId(),
                    dbFile.getFileType(),
                    dbFile.getDateCreated()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/ByEmIdAndType")
    public ResponseEntity<?> getListFilesByEmIdAndType(@RequestParam("emId") Long emId, @RequestParam("type") Integer type) {
        List<ResponseFile> files = fileService.getListFileByEmIdAndType(emId, type).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/")
                    .path(dbFile.getFileId())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getFileName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getFile().length,
                    dbFile.getEmId(),
                    dbFile.getFileType(),
                    dbFile.getDateCreated()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
    @GetMapping("/files/ByEmIdAndTypeAndDate")
    public ResponseEntity<?> getListFilesByEmIdAndTypeAndDate(@RequestParam("emId") Long emId,
                                                              @RequestParam("type") Integer type,
                                                              @RequestParam("date") LocalDate date) {
        List<ResponseFile> files = fileService.getListFileByEmIdAndTypeAndDate(date, emId, type).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/")
                    .path(dbFile.getFileId())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getFileName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getFile().length,
                    dbFile.getEmId(),
                    dbFile.getFileType(),
                    dbFile.getDateCreated()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

}
