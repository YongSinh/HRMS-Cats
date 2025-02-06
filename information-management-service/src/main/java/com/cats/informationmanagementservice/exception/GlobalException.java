package com.cats.informationmanagementservice.exception;

import com.cats.informationmanagementservice.base.BaseError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@ControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public BaseError<?> handleServiceException(AccessDeniedException e) {
        return BaseError.builder()
                .status(false)
                .code(HttpStatus.FORBIDDEN.value())
                .message("Something went wrong, please check in error detail!")
                .timestamp(LocalDateTime.now())
                .error(e.getReason())
                .build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseError<?> handleValidationException(MethodArgumentNotValidException e) {
        List<Map<String, String>> errors = new ArrayList<>();

        for (FieldError fieldError : e.getFieldErrors()) {
            Map<String, String> error = new HashMap<>();
            error.put("field", fieldError.getField());
            error.put("detail", fieldError.getDefaultMessage());
            errors.add(error);
        }
        return BaseError.builder().status(false).code(HttpStatus.BAD_REQUEST.value()).message("Something field may empty, please check in error detail!").timestamp(LocalDateTime.now()).error(errors).build();
    }


    @ExceptionHandler(ConstraintViolationException.class)
    protected BaseError<?> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder errorMessage = new StringBuilder();
        e.getConstraintViolations().forEach(violation -> {
            errorMessage.append(violation.getMessage()).append(", ");
        });
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return BaseError.builder().status(false).code(HttpStatus.BAD_REQUEST.value()).message(String.valueOf(errorMessage)).timestamp(LocalDateTime.now()).error(errors).build();
    }

    @ExceptionHandler({IllegalStateException.class})
    public BaseError<?> handleIllegalStateException(IllegalStateException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("IllegalStateException", e.getMessage());
        return BaseError.builder().status(false).code(HttpStatus.NOT_FOUND.value()).message(e.getCause().getMessage()).timestamp(LocalDateTime.now()).error(errors).build();
    }

    @ExceptionHandler(SQLException.class)
    public BaseError<?> handleSQLException(SQLException e) {
//        Map<String, String> errors = new HashMap<>();
//        errors.put("SQLError", e.getMessage());
        return BaseError.builder().status(false).code(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .error("SQLError: " + e.getSQLState()).build();
    }

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

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public BaseError<?> NotFound(HttpClientErrorException.NotFound e) {
        return BaseError.builder()
                .status(false)
                .code(HttpStatus.NOT_FOUND.value())
                .message("Something went wrong, please check in error detail!")
                .timestamp(LocalDateTime.now())
                .error(e.getLocalizedMessage())
                .build();
    }

}
