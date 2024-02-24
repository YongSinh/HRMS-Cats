package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.Dto.DepartmentDtoRep;
import com.cats.informationmanagementservice.Dto.JobHistoryDtoReq;
import com.cats.informationmanagementservice.Dto.PositionDtoReq;
import com.cats.informationmanagementservice.base.BaseApi;
import com.cats.informationmanagementservice.model.JobHistory;
import com.cats.informationmanagementservice.model.Position;
import com.cats.informationmanagementservice.service.JobHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/info/jobHistory")
@RequiredArgsConstructor
public class JobHistoryController {

    private final JobHistoryService jobHistoryService;
    @PostMapping("/addJobHistory")
    public BaseApi<?> addJobHistory(@RequestBody JobHistoryDtoReq jobHistoryDtoReq) {
      JobHistory jobHistory = jobHistoryService.create(jobHistoryDtoReq);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("job Histories have been created")
                .timestamp(LocalDateTime.now())
                .data(jobHistory)
                .build();
    }

    @PutMapping("/editJobHistory/{Id}")
    public BaseApi<?> editJobHistory(@RequestBody JobHistoryDtoReq jobHistoryDtoReq, @PathVariable Long Id) {
        JobHistory jobHistory = jobHistoryService.edit(jobHistoryDtoReq, Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("job Histories have been edited")
                .timestamp(LocalDateTime.now())
                .data(jobHistory)
                .build();
    }

    @GetMapping("/jobHistory")
    public BaseApi<?> jobHistories() {
        List<JobHistory> jobHistories = jobHistoryService.getListJobHistory();
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Job history have been found")
                .timestamp(LocalDateTime.now())
                .data(jobHistories)
                .build();
    }
    @DeleteMapping("/deleteJobHistory/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteJobHistory(@PathVariable Long id) {
        jobHistoryService.delete(id);
        return new ResponseEntity<>("This Job History with Id: "+id +" have been deleted", HttpStatus.OK);
    }
    @GetMapping("/jobHistoryById/{Id}")
    public BaseApi<?> jobHistoryById(@PathVariable Long Id) {
        JobHistory jobHistories = jobHistoryService.getById(Id);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Job history have been found")
                .timestamp(LocalDateTime.now())
                .data(jobHistories)
                .build();
    }
}
