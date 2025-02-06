package com.cats.attendanceservice.model;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class NotificationMessage {
    private String message;
    private String sender;
}
