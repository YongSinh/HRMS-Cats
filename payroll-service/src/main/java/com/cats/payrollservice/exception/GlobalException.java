package com.cats.payrollservice.exception;

import com.cats.payrollservice.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class GlobalException {
    @ExceptionHandler(IllegalArgumentException.class)
    public BaseError<?> handleMainException(IllegalArgumentException e) {
        return BaseError.builder().status(false)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .error(e.getMessage())
                .build();
    }

    @ExceptionHandler(NullPointerException.class)
    public BaseError<?> NullPointerException(NullPointerException e) {
        return BaseError.builder()
                .status(false)
                .code(HttpStatus.OK.value())
                .message("Something went wrong, please check in error detail!")
                .timestamp(LocalDateTime.now())
                .error(e.getLocalizedMessage())
                .build();
    }
}
