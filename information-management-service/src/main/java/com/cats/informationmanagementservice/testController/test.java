package com.cats.informationmanagementservice.testController;

import com.cats.informationmanagementservice.base.BaseApi;
import com.cats.informationmanagementservice.events.MessageFull;
import com.cats.informationmanagementservice.listener.KafKaProducerService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

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