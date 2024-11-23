package com.cats.attendanceservice.listener;

import com.cats.attendanceservice.model.MessageFull;
import com.cats.attendanceservice.model.NotificationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafKaProducerService {

    //1. General topic with string payload

    @Value(value = "${general.topic.name}")
    private String topicName;


    private final  KafkaTemplate<String, NotificationMessage> kafkaTemplate;
    private final  KafkaTemplate<String, MessageFull> messageFullKafkaTemplate;

    //2. Topic with user object payload

    @Value(value = "${user.topic.name}")
    private String userTopicName;


    public void sendMessage(String message, String sender) {
        NotificationMessage notificationMessage = new NotificationMessage(message, sender);
        CompletableFuture<SendResult<String, NotificationMessage>> future = kafkaTemplate.send("notificationTopic", notificationMessage);
        log.info("Attempting to send message: {}", message);

        future.whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("Unable to send message: {}", message, throwable);
            } else {
                log.info("Sent message: {} with offset: {}", message, result.getRecordMetadata().offset());
            }
        });
    }

    public void senGendMessage(MessageFull messageFull) {
        MessageFull message = new MessageFull();
        LocalDateTime localDateTime = LocalDateTime.now();
        message.setEnglishText(messageFull.getEnglishText());
        message.setDateTime(localDateTime);
        message.setSender(messageFull.getSender());
        CompletableFuture<SendResult<String, MessageFull>> future = messageFullKafkaTemplate.send("generalTopic", message);
        log.info("Attempting to send message: {}", message);

        future.whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("Unable to send message: {}", message, throwable);
            } else {
                log.info("Sent message: {} with offset: {}", message, result.getRecordMetadata().offset());
            }
        });
    }


}
