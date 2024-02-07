package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.Dto.DepartmentDtoReq;
import com.cats.informationmanagementservice.Dto.PositionDtoReq;
import com.cats.informationmanagementservice.base.BaseApi;
import com.cats.informationmanagementservice.model.Department;
import com.cats.informationmanagementservice.model.Position;
import com.cats.informationmanagementservice.service.DepartmentService;
import com.cats.informationmanagementservice.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/position")
@RequiredArgsConstructor
public class PositionController {
    private final PositionService positionService;
    @PostMapping("/addPosition")
    public BaseApi<?> addPosition(@RequestBody PositionDtoReq positionDtoReq) {
        Position position = positionService.addPosition(positionDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account types have been found")
                .timestamp(LocalDateTime.now())
                .data(position)
                .build();
    }

}
