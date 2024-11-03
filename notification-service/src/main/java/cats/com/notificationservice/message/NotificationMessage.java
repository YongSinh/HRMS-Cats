package cats.com.notificationservice.message;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationMessage {
    private String message;
    private String sender;
}
