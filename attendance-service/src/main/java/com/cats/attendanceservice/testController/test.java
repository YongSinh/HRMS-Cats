package com.cats.attendanceservice.testController;

import com.cats.attendanceservice.base.BaseApi;
import com.cats.attendanceservice.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/api/attendanceLeave")
@RequiredArgsConstructor
@Slf4j
public class test {
    private final ApiService apiService;
    @GetMapping("/test")
    public BaseApi<?> getListAllEmployeeOnlyEmId() throws IOException {
        Collection<Long> getList = apiService.getListAllEmployeeOnlyEmId();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List All the Attendance")
                .timestamp(LocalDateTime.now())
                .data(getList)
                .build();
    }
}