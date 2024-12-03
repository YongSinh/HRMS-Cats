package com.cats.apigeteway.conf;

import com.cats.apigeteway.base.BaseApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class FallbackController {
    @RequestMapping("/fallback")
    public BaseApi<?> fallback() {
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Service is temporarily unavailable. Please try again later."+ HttpStatus.SERVICE_UNAVAILABLE)
                .timestamp(LocalDateTime.now())
                .data(null)
                .build();
    }
}
