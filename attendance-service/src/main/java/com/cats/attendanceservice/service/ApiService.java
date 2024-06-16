package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.WebFluxResponse;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {
    private final WebClient.Builder webClientBuilder;
    public Collection<Long> getEmployeeByUnderMangerOnlyEmId(Long emId) {
        WebFluxResponse response = webClientBuilder.build().get()
                .uri("http://information-management-service/api/info/employee/getEmployeeByUnderMangerOnlyEmId",
                        uriBuilder -> uriBuilder.queryParam("emId", emId).build())
                .retrieve()
                .bodyToMono(WebFluxResponse.class)
                .block();
        if (response != null && response.getCode() == 200) {
            JsonNode emIdList = response.getData();
            List<Long> idList = new ArrayList<>();
            if (emIdList.isArray()) {
                for (JsonNode node : emIdList) {
                    idList.add(node.asLong());
                }
            }
            return idList;
        } else {
            System.out.println("No employee IDs found or response status is not OK.");
            return List.of();
        }
    }
}
