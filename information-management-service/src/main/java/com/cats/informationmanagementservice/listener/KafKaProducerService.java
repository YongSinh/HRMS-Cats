package com.cats.informationmanagementservice.listener;

import com.cats.informationmanagementservice.events.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafKaProducerService {

    //1. General topic with string payload

    @Value(value = "${general.topic.name}")
    private String topicName;


    private final KafkaTemplate<String, String> kafkaTemplate;

    //2. Topic with user object payload

    @Value(value = "${user.topic.name}")
    private String userTopicName;


    public void sendMessage(String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("test", message);
        System.out.println("Sent message: {} with offset: {}"+ message);
        future.whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("Unable to send message: " + message, throwable);
            } else {
                log.info("Sent message: {} with offset: {}", message, result.getRecordMetadata().offset());
            }
        });
    }

}
