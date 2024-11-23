package cats.com.notificationservice.service;

import cats.com.notificationservice.controller.CommandController;
import cats.com.notificationservice.controller.WebSocketController;
import cats.com.notificationservice.message.Message;
import cats.com.notificationservice.message.MessageFull;
import cats.com.notificationservice.message.MessageType;
import cats.com.notificationservice.message.NotificationMessage;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafKaConsumerService {

    private final WebSocketController webSocketController;
    private final ObservationRegistry observationRegistry;
    private final CommandController commandController;
    private final MessageService messageService;
	@KafkaListener(topics = "${notification.topic.name}", groupId = "${notification.topic.group.id}", containerFactory = "notificationMessageKafkaListenerContainerFactory")
    public void consume(NotificationMessage notificationMessage) {
        log.info("Message received -> {}", notificationMessage.getMessage());

        // Push the message to WebSocket clients
        Message messageObject = new Message();
        messageObject.setContent(notificationMessage.getMessage());
        messageObject.setType(MessageType.CHAT);
        messageObject.setSender(notificationMessage.getSender());

        MessageFull messageFull = new MessageFull();
        messageFull.setId(1);
        messageFull.setEnglishText(notificationMessage.getMessage());
        messageFull.setSender(notificationMessage.getSender());
        messageFull.setMessageType(MessageType.CHAT.toString());
        messageFull.setIsRead(false);
        //webSocketController.sendGenMessage(messageFull);
        //webSocketController.sendKafkaMessage(messageObject);
        commandController.send(messageObject);
    }

    @KafkaListener(topics = "${general.topic.name}", groupId = "${general.topic.group.id}", containerFactory = "messageFullKafkaListenerContainerFactory")
    public void consumeGen(MessageFull message) {
        webSocketController.sendGenMessage(message);
       // messageService.saveMessage(messageFull);
        System.out.println("Sent message: {} with test offset: {}"+ message.getEnglishText());
    }

    @KafkaListener(topics = "messageGen", groupId = "${general.topic.group.id}", containerFactory = "messageFullKafkaListenerContainerFactory")
    public void consumeGen2(MessageFull message) {
        MessageFull messageFull = new MessageFull();
        messageFull.setEnglishText(message.getEnglishText());
        messageFull.setSender(message.getSender());
        messageFull.setMessageType(MessageType.GENERAL.toString());
        messageFull.setIsRead(false);
        messageFull.setDateTime(message.getDateTime());
        messageService.saveMessage(messageFull);
        System.out.println("Sent message: {} with test offset: {}"+ message.getEnglishText());
    }

}
