package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.Dto.DepartmentDtoReq;
import com.cats.informationmanagementservice.Dto.PositionDtoRep;
import com.cats.informationmanagementservice.Dto.PositionDtoReq;
import com.cats.informationmanagementservice.base.BaseApi;
import com.cats.informationmanagementservice.model.Department;
import com.cats.informationmanagementservice.model.Position;
import com.cats.informationmanagementservice.service.DepartmentService;
import com.cats.informationmanagementservice.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/info/position")
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
    @GetMapping("/position")
    public BaseApi<?> Position() {
        List<PositionDtoRep> position = positionService.getListPosition();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account types have been found")
                .timestamp(LocalDateTime.now())
                .data(position)
                .build();
    }
    @GetMapping("/getPositionById/{id}")
    public BaseApi<?> getPositionById( @PathVariable String id) {
        Position position = positionService.getPositionById(id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account types have been found")
                .timestamp(LocalDateTime.now())
                .data(position)
                .build();
    }

    @GetMapping("/getPositionByDepId")
    public BaseApi<?> getPositionByDepId(@RequestParam Long depId) {
        List<PositionDtoRep> position = positionService.getLisPositionByDepId(depId);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account types have been found")
                .timestamp(LocalDateTime.now())
                .data(position)
                .build();
    }

    @PutMapping("/editPosition/{id}")
    public BaseApi<?> editPosition(@RequestBody PositionDtoReq positionDtoReq, @PathVariable String id) {
        Position position = positionService.editPosition(positionDtoReq, id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account types have been found")
                .timestamp(LocalDateTime.now())
                .data(position)
                .build();
    }

}
