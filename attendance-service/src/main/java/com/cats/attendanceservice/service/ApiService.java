package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.WebFluxResponse;
import com.cats.attendanceservice.events.ListEmpByEmpIdEvent;
import com.cats.attendanceservice.listener.KafKaProducerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService {
    private final WebClient.Builder webClientBuilder;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final KafKaProducerService kafKaProducerService;
    public Collection<Long> getEmployeeByUnderMangerOnlyEmId(Long emId) {
        // Send message to Kafka
        //kafKaProducerService.sendMessage(emId.toString());

        // Make the WebClient call
        Flux<WebFluxResponse> responseFlux = webClientBuilder.build().get()
                .uri("http://information-management-service/api/info/employee/getEmployeeByUnderMangerOnlyEmId",
                        uriBuilder -> uriBuilder.queryParam("emId", emId).build())
                .retrieve()
                .bodyToFlux(WebFluxResponse.class);

        // Subscribe to the flux to handle the response asynchronously
        List<Long> idList = responseFlux.flatMap(response -> {
            if (response.getCode() == 200) {
                JsonNode emIdList = response.getData();
                List<Long> ids = new ArrayList<>();
                if (emIdList.isArray()) {
                    for (JsonNode node : emIdList) {
                        ids.add(node.asLong());
                    }
                }
                return Flux.fromIterable(ids);
            } else {
                System.out.println("No employee IDs found or response status is not OK.");
                return Flux.empty();
            }
        }).collectList().block(); // block() here to wait for the completion and collect the list

        return idList != null ? idList : Collections.emptyList();
    }


    public Collection<Long> getListAllEmployeeOnlyEmId() throws IOException {
        WebFluxResponse response = webClientBuilder.build().get()
                .uri("http://information-management-service/api/info/employee/listEmployee")
                .retrieve()
                .bodyToMono(WebFluxResponse.class)
                .block();
        if (response != null && response.getCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonArray = objectMapper.readTree(response.getData().traverse());
            List<Long> idList = new ArrayList<>();

            if (jsonArray.isArray()) {
                for (JsonNode employeeNode : jsonArray) {
                    JsonNode empIdNode = employeeNode.get("empId");
                    if (empIdNode != null) {
                        idList.add(empIdNode.asLong());
                    }
                }
            }
            return idList;
        } else {
            System.out.println("No employee IDs found or response status is not OK.");
            return Collections.emptyList();
        }
    }

    public JsonNode getEmployeeInFoByEmId(Long emId) throws IOException {
        WebFluxResponse response = webClientBuilder.build().get()
                .uri("http://information-management-service/api/info/employee/getEmpInfoById",
                        uriBuilder -> uriBuilder.queryParam("emId", emId).build()
                        )
                .retrieve()
                .bodyToMono(WebFluxResponse.class)
                .block();
        if (response != null && response.getCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonArray = objectMapper.readTree(response.getData().traverse());
            System.out.println(jsonArray);
            return jsonArray;
        } else {
            System.out.println("No employee IDs found or response status is not OK.");
            return null;
        }
    }

}
