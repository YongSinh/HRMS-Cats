package com.cats.informationmanagementservice.listener;

import com.cats.informationmanagementservice.events.MessageFull;
import com.cats.informationmanagementservice.events.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    private final  KafkaTemplate<String, MessageFull> messageFullKafkaTemplate;

    //2. Topic with user object payload

    @Value(value = "${user.topic.name}")
    private String userTopicName;


    public void senGendMessage(MessageFull messageFull) {
        CompletableFuture<SendResult<String, MessageFull>> future = messageFullKafkaTemplate.send("generalTopic", messageFull);
        log.info("Attempting to send message: {}", messageFull);

        future.whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("Unable to send message: {}", messageFull, throwable);
            } else {
                log.info("Sent message: {} with offset: {}", messageFull, result.getRecordMetadata().offset());
            }
        });
    }


}
