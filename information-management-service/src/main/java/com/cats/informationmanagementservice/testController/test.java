package com.cats.informationmanagementservice.testController;

import com.cats.informationmanagementservice.events.MessageFull;
import com.cats.informationmanagementservice.listener.KafKaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info/test")
@RequiredArgsConstructor
@Slf4j
public class test {

    private final KafKaProducerService producerService;

    @PostMapping(value = "/publish/gen")
    public void sendMessageToKafkaTopicGen(@RequestBody MessageFull messageFull) {
        producerService.senGendMessage(messageFull);
    }
}