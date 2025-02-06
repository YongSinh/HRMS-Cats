package cats.com.notificationservice.controller;

import cats.com.notificationservice.message.Message;
import cats.com.notificationservice.message.MessageFull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final KafkaTemplate<String, MessageFull> messageFullKafkaTemplate;

    @Value("${general.topic.name}")
    private String topic;

    @PostMapping("/send/kafkaMessages")
    public void sendKafkaMessage(@RequestBody Message message) {
        kafkaTemplate.send("messaging", message);
        messagingTemplate.convertAndSend("/topic/kafkaMessages", message);
    }

    @PostMapping("/send/genMessage")
    public void sendGenMessage(@RequestBody MessageFull message) {
        messageFullKafkaTemplate.send("messageGen", message);
        messagingTemplate.convertAndSend("/topic/genMessage", message);
    }
}
