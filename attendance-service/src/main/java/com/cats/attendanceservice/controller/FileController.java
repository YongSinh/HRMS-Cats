package com.cats.attendanceservice.controller;

import com.cats.attendanceservice.dto.RequestFile;
import com.cats.attendanceservice.dto.RequestFileUpdate;
import com.cats.attendanceservice.dto.ResponseFile;
import com.cats.attendanceservice.model.FileInfo;
import com.cats.attendanceservice.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            fileService.store(file,requestFile.getEmId(), requestFile.getType(), requestFile.getDateCreated(), requestFile.getServiceType());
            String message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
            String message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @RequestMapping(value = "/files/updateFile", method = RequestMethod.POST, consumes = { "multipart/form-data"})
    public ResponseEntity<?> uploadUpdateFile(@RequestPart("file") @Valid MultipartFile file, @RequestPart("body")  @Valid RequestFileUpdate requestFile){
        try {
            fileService.updateStore(file,requestFile.getEmId(), requestFile.getType(), requestFile.getDateCreated(), requestFile.getServiceType(), requestFile.getId());
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
                fileService.store(file,requestFile.getEmId(), requestFile.getType(), requestFile.getDateCreated(), requestFile.getServiceType());
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


//    @GetMapping("/files/preview/{id}")
//    public ResponseEntity<byte[]> previewFile(@PathVariable String id) throws IOException {
//        FileInfo fileDB = fileService.getFile(id);
//        String contentType = fileDB.getFileType(); // Ensure this is set correctly in your DbFile
//
//        // If content type is not set, try to determine it from the file extension or data
//        if (contentType == null || contentType.isEmpty()) {
//            contentType = Files.probeContentType(Paths.get(fileDB.getFileName()));
//            if (contentType == null) {
//                contentType = "application/octet-stream";  // Default to binary if unknown
//            }
//        }

    
//        // Set Content-Disposition to inline for browser preview
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileDB.getFileName() + "\"")
//                .body(fileDB.getFile());
//    }

    @GetMapping("/files/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName) {
        Resource resource = fileService.downloadFile(fileName);

        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        // Try to determine the file's content type
        String contentType = "application/octet-stream";  // Default for unknown file types
        try {
            contentType = Files.probeContentType(resource.getFile().toPath());
            if (contentType == null) {
                contentType = "application/octet-stream";  // Fallback if no content type is detected
            }
        } catch (IOException e) {
            System.out.println("Could not determine file type.");
        }

        // Use 'inline' instead of 'attachment' for viewing the file in the browser
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(resource);
    }


//    @GetMapping("/files/{id}")
//    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//        FileInfo fileDB = fileService.getFile(id);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFileName() + "\"")
//                .body(fileDB.getFile());
//    }

    @GetMapping("/files")
    public ResponseEntity<?> getListFiles() {
        List<ResponseFile> files = fileService.getAllFile().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/view/")
                    .path(dbFile.getFileName())
                    .toUriString();
            // Generate preview link
            String filePreviewUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/view/")
                    .path(dbFile.getFileName())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getFileId(),
                    dbFile.getFileName(),
                    fileDownloadUri,
                    filePreviewUri,
                    dbFile.getType(),
                    dbFile.getFileSize(),
                    dbFile.getEmId(),
                    dbFile.getFileType(),
                    dbFile.getDateCreated(),
                    dbFile.getServiceType()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/ByEmId")
    public ResponseEntity<?> getListFilesByEmId(@RequestParam("emId")  Long emId) {
        List<ResponseFile> files = fileService.getAllFileByEmId(emId).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                   .path("/api/files/view/")
                    .path(dbFile.getFileName())
                    .toUriString();
            String filePreviewUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/view/")
                    .path(dbFile.getFileName())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getFileId(),
                    dbFile.getFileName(),
                    fileDownloadUri,
                    filePreviewUri,
                    dbFile.getType(),
                      dbFile.getFileSize(),
                    dbFile.getEmId(),
                    dbFile.getFileType(),
                    dbFile.getDateCreated(),
                    dbFile.getServiceType()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/ByEmIdAndType")
    public ResponseEntity<?> getListFilesByEmIdAndType(@RequestParam("emId") Long emId, @RequestParam("type") Integer type) {
        List<ResponseFile> files = fileService.getListFileByEmIdAndType(emId, type).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                   .path("/api/files/view/")
                    .path(dbFile.getFileName())
                    .toUriString();
            String filePreviewUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/view/")
                    .path(dbFile.getFileName())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getFileId(),
                    dbFile.getFileName(),
                    fileDownloadUri,
                    filePreviewUri,
                    dbFile.getType(),
                      dbFile.getFileSize(),
                    dbFile.getEmId(),
                    dbFile.getFileType(),
                    dbFile.getDateCreated(),
                    dbFile.getServiceType()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/ByEmIdAndTypeService")
    public ResponseEntity<?> getListFilesByEmIdAndTypeService(@RequestParam("emId") Long emId, @RequestParam("type") Integer type, @RequestParam("service") Integer service) {
        List<ResponseFile> files = fileService.getListFileByEmIdWithServiceType(emId, type, service).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                   .path("/api/files/view/")
                    .path(dbFile.getFileName())
                    .toUriString();
            String filePreviewUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/view/")
                    .path(dbFile.getFileName())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getFileId(),
                    dbFile.getFileName(),
                    fileDownloadUri,
                    filePreviewUri,
                    dbFile.getType(),
                    dbFile.getFileSize(),
                    dbFile.getEmId(),
                    dbFile.getFileType(),
                    dbFile.getDateCreated(),
                    dbFile.getServiceType()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }



    @GetMapping("/files/ByEmIdAndTypeServiceDate")
    public ResponseEntity<?> getListFilesByEmIdAndTypeAndDate(@RequestParam("emId") Long emId,
                                                              @RequestParam("type") Integer type,
                                                              @RequestParam("date") LocalDate date,
                                                              @RequestParam("service") Integer service
                                                              ) {
        List<ResponseFile> files = fileService.getListFileByEmIdAndTypeServiceAndDate(date, emId, type, service).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                   .path("/api/files/view/")
                    .path(dbFile.getFileName())
                    .toUriString();
            String filePreviewUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/view/")
                    .path(dbFile.getFileName())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getFileId(),
                    dbFile.getFileName(),
                    fileDownloadUri,
                    filePreviewUri,
                    dbFile.getType(),
                    dbFile.getFileSize(),
                    dbFile.getEmId(),
                    dbFile.getFileType(),
                    dbFile.getDateCreated(),
                    dbFile.getServiceType()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

}
