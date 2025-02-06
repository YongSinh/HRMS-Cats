package com.cats.attendanceservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class MessageFull {
    private Integer id;
    private String khmerText;
    private String englishText;
    private String sender;
    private String receiver;
    private String messageType;
    private String sessionId;
    private LocalDateTime dateTime;
    private String serviceType;
    private Boolean isRead;
    private String status;
    private String attachments;
    private String priority;
    private LocalDateTime editedAt;
    private LocalDateTime deletedAt;
}
