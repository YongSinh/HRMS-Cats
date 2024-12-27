package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.WebFluxResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public String getEmpName(Long emId) {
        // Asynchronously retrieve the employee name
        Mono<String> nameMono = webClientBuilder.build().get()
                .uri("http://information-management-service/api/info/employee/getEmployeeName",
                        uriBuilder -> uriBuilder.queryParam("emId", emId).build())
                .retrieve()
                .bodyToMono(WebFluxResponse.class)
                .flatMap(response -> {
                    if (response.getCode() == 200) {
                        // Extract the employee name from the response data
                        JsonNode data = response.getData();
                        return Mono.just(data.asText());
                    } else {
                        System.out.println("Failed to retrieve employee name, code: " + response.getCode());
                        return Mono.empty();
                    }
                });

        // Block only if absolutely necessary, otherwise return Mono<String> for non-blocking usage
        return nameMono.block(); // Avoid using block() unless you are in a non-reactive context
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
