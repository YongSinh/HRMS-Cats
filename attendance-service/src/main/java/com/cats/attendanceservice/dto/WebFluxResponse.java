package com.cats.attendanceservice.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebFluxResponse {
    private boolean status;
    private int code;
    private String message;
    private String timestamp;
    private JsonNode data;
}
