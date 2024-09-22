package com.cats.attendanceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseFile {
    private String name;
    private String url;
    private String filePreviewUri;
    private Integer type;
    private long size;
    private Long emId;
    private String fileType;
    private LocalDate dateCreated;
    private Integer serviceType;
}


