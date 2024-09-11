package com.cats.payrollservice.base;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BaseError  <T> (Boolean status,
                             Integer code,
                             String message,
                             LocalDateTime timestamp,
                             String error,
                             T data){
}
