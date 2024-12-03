package cats.com.notificationservice.message;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "message")
@Entity
public class MessageFull {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "khmer_text", length = 255, columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String khmerText;

    @Column(name = "english_text", length = 255)
    private String englishText;

    @Column(name = "sender", length = 50)
    private String sender;

    @Column(name = "receiver", length = 50)
    private String receiver;

    @Column(name = "messageType", length = 50)
    private String messageType;

    @Column(name = "sessionId", length = 255)
    private String sessionId;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "serviceType", length = 100)
    private String serviceType;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "attachments", length = 255)
    private String attachments;

    @Column(name = "priority", length = 20)
    private String priority;

    @Column(name = "edited_at")
    private LocalDateTime editedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
