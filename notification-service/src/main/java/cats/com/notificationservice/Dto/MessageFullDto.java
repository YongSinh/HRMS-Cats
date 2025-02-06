package cats.com.notificationservice.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageFullDto {
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
