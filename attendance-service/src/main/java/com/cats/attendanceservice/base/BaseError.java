package com.cats.attendanceservice.base;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BaseError<T>(
        Boolean status,
        Integer code,
        String message,
        LocalDateTime timestamp,
        T error) {
}