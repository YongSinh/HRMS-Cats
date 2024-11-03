package cats.com.notificationservice.service;

import brave.Tracer;
import cats.com.notificationservice.controller.CommandController;
import cats.com.notificationservice.controller.WebSocketController;
import cats.com.notificationservice.message.Message;
import cats.com.notificationservice.message.MessageType;
import cats.com.notificationservice.message.NotificationMessage;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafKaConsumerService {

    private final WebSocketController webSocketController;
    private final ObservationRegistry observationRegistry;
    private final CommandController commandController;
	@KafkaListener(topics = "${general.topic.name}", groupId = "${general.topic.group.id}", containerFactory = "notificationMessageKafkaListenerContainerFactory")
    public void consume(NotificationMessage notificationMessage) {
        log.info("Message received -> {}", notificationMessage.getMessage());

        // Push the message to WebSocket clients
        Message messageObject = new Message();
        messageObject.setContent(notificationMessage.getMessage());
        messageObject.setType(MessageType.CHAT);
        messageObject.setSender(notificationMessage.getSender());

        webSocketController.sendKafkaMessage(messageObject);
        commandController.send(messageObject);
    }

    @KafkaListener(topics = "test", groupId = "${general.topic.group.id}", containerFactory = "kafkaListenerContainerFactory")
    public void consumeTest(String message) {
        System.out.println("Sent message: {} with test offset: {}"+ message);
        log.info(String.format("Message received test-> %s", message));
    }

    @KafkaListener(topics = "hr", groupId = "hr", containerFactory = "kafkaListenerContainerFactory")
    public void consumerHr(String message) {
        log.info(String.format("Message received test-> %s", message));
    }



//    @KafkaListener(topics = "${user.topic.name}",
//            groupId = "${user.topic.group.id}",
//            containerFactory = "userKafkaListenerContainerFactory")
//    public void consume(User user) {
//        log.info(String.format("User created -> %s", user));
//    }
}
