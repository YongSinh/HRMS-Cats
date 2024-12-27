package com.cats.attendanceservice.testController;

import com.cats.attendanceservice.base.BaseApi;
import com.cats.attendanceservice.listener.KafKaProducerService;
import com.cats.attendanceservice.model.MessageFull;
import com.cats.attendanceservice.service.ApiService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/api/attendanceLeave/test")
@RequiredArgsConstructor
@Slf4j
public class test {
    private final ApiService apiService;
    private final KafKaProducerService producerService;

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message, @RequestParam("sender") String sender) {
        producerService.sendMessage(message,sender);
    }

    @PostMapping(value = "/publish2")
    public void sendMessageToKafkaTopic2(@RequestBody MessageFull message) {
        producerService.senGendMessage(message);
    }
    @GetMapping("/test")
    public BaseApi<?> getListAllEmployeeOnlyEmId(@RequestParam Long emId) throws IOException {
        JsonNode getList = apiService.getEmployeeInFoByEmId(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List All the Attendance")
                .timestamp(LocalDateTime.now())
                .data(getList.get("emId"))
                .build();
    }

    @GetMapping("/name")
    public BaseApi<?> getName(@RequestParam Long emId) throws IOException {
        String name = apiService.getEmpName(emId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List All the Attendance")
                .timestamp(LocalDateTime.now())
                .data(name)
                .build();
    }

}