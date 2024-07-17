package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.WebFluxResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.*;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class ApiService {
    private final WebClient.Builder webClientBuilder;
    public Flux<Long> getListAllEmployeeOnlyEmIdTest() {
        return webClientBuilder.build().get()
                .uri("http://information-management-service/api/info/employee/listEmployee")
                .retrieve()
                .bodyToFlux(WebFluxResponse.class)
                .flatMap(response -> {
                    if (response != null && response.getCode() == 200) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonArray = null;
                        try {
                            jsonArray = objectMapper.readTree(response.getData().traverse());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (jsonArray.isArray()) {
                            return Flux.fromStream(
                                    StreamSupport.stream(jsonArray.spliterator(), false)
                                            .map(employeeNode -> employeeNode.get("empId"))
                                            .filter(Objects::nonNull)
                                            .map(JsonNode::asLong)
                            );
                        }
                    }
                    return Flux.empty();
                });
    }

    public Collection<Long> getListAllEmployeeOnlyEmId() throws IOException {
        Flux<WebFluxResponse> responseFlux = webClientBuilder.build().get()
                .uri("http://information-management-service/api/info/employee/listEmployee")
                .retrieve()
                .bodyToFlux(WebFluxResponse.class);

        List<Long> idList = new ArrayList<>();

        responseFlux.toIterable().forEach(response -> {
            if (response != null && response.getCode() == 200) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonArray = objectMapper.readTree(response.getData().traverse());

                    if (jsonArray.isArray()) {
                        for (JsonNode employeeNode : jsonArray) {
                            JsonNode empIdNode = employeeNode.get("empId");
                            if (empIdNode != null) {
                                idList.add(empIdNode.asLong());
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error processing response: " + e.getMessage());
                }
            } else {
                System.out.println("No employee IDs found or response status is not OK.");
            }
        });

        return idList;
    }



}
