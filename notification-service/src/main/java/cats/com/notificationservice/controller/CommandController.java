package cats.com.notificationservice.controller;

import cats.com.notificationservice.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommandController {
    private final KafkaTemplate<String, Message> messageKafkaTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @PostMapping("/api/notification/send")
    public void send(@RequestBody Message message) {
        messageKafkaTemplate.send("messaging", message);
        messagingTemplate.convertAndSend("/topic/public", message);
    }


}
