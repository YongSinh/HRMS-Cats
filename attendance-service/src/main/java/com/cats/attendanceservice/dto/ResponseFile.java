package com.cats.attendanceservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseFile {
    private String name;
    private String url;
    private Integer type;
    private long size;
    private Long emId;
    private String fileType;




}


