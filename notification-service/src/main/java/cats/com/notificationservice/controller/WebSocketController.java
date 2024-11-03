package cats.com.notificationservice.controller;

import cats.com.notificationservice.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final KafkaTemplate<String, Message> kafkaTemplate;
    @PostMapping("/send/kafkaMessages")
    public void sendKafkaMessage(@RequestBody Message message) {
        kafkaTemplate.send("messaging", message);
        messagingTemplate.convertAndSend("/topic/kafkaMessages", message);
    }
}
