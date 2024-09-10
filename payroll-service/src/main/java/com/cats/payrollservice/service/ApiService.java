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

//    public Collection<Long> getEmployeeByDepId(Long depId)  {
//        Flux<WebFluxResponse> responseFlux = webClientBuilder.build().get()
//                .uri("http://information-management-service/api/info/employee/listEmployeeByDep",
//                        uriBuilder -> uriBuilder.queryParam("depId", depId).build()
//                )
//                .retrieve()
//                .bodyToFlux(WebFluxResponse.class);
//
//        List<Long> idList = new ArrayList<>();
//        responseFlux.toIterable().forEach(response -> {
//            if (response != null && response.getCode() == 200) {
//                try {
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    JsonNode jsonArray = objectMapper.readTree(response.getData().traverse());
//
//                    if (jsonArray.isArray()) {
//                        for (JsonNode employeeNode : jsonArray) {
//                            JsonNode empIdNode = employeeNode.get("empId");
//                            if (empIdNode != null) {
//                                idList.add(empIdNode.asLong());
//                            }
//                        }
//                    }
//                } catch (IOException e) {
//                    System.out.println("Error processing response: " + e.getMessage());
//                }
//            } else {
//                System.out.println("No employee IDs found or response status is not OK.");
//            }
//        });
//
//        return idList;
//    }

    public Collection<Long> getEmployeeByDepId(Long depId)  {
        Flux<WebFluxResponse> responseFlux = webClientBuilder.build().get()
                .uri("http://information-management-service/api/info/employee/listEmployeeByDep",
                        uriBuilder -> uriBuilder.queryParam("depId", depId).build()
                )
                .retrieve()
                .bodyToFlux(WebFluxResponse.class);

        List<Long> idList = new ArrayList<>();

        responseFlux.toIterable().forEach(response -> {
            // Check for null and 404 code
            if (response != null) {
                if (response.getCode() == 404) {
                    // If status code is 404, return empty list
                    System.out.println("No employees found for the given department. Returning empty list.");
                } else if (response.getCode() == 200) {
                    // Process the data if status is 200
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
                    System.out.println("Unexpected response status code: " + response.getCode());
                }
            } else {
                System.out.println("Response is null or invalid.");
            }
        });

        return idList; // This will be an empty list if 404 was encountered
    }

}
