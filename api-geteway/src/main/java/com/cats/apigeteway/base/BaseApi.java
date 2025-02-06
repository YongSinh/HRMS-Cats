package com.cats.apigeteway.base;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BaseApi
        <T>(Boolean status,
            Integer code,
            String message,
            LocalDateTime timestamp,
            T data) {
}
