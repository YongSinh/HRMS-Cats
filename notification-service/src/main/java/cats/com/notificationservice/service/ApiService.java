package cats.com.notificationservice.service;

import cats.com.notificationservice.Dto.WebFluxResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final WebClient.Builder webClientBuilder;

    public Collection<Long> getListEmId() throws IOException {
        // Call the external API
        WebFluxResponse response = webClientBuilder.build().get()
                .uri("http://information-management-service/api/info/employee/listEmployeeId")
                .retrieve()
                .bodyToMono(WebFluxResponse.class)
                .block();

        // Process the response
        if (response != null && response.getCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the response's data field as JSON
            JsonNode jsonArray = objectMapper.readTree(objectMapper.writeValueAsString(response.getData()));

            // Convert JsonNode to a list of Longs
            List<Long> employeeIds = new ArrayList<>();
            if (jsonArray.isArray()) {
                for (JsonNode node : jsonArray) {
                    employeeIds.add(node.asLong());
                }
            }
            return employeeIds;
        } else {
            System.out.println("No employee IDs found or response status is not OK.");
            return Collections.emptyList();
        }
    }
}
