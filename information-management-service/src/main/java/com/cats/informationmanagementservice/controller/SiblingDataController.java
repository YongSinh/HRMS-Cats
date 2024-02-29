package com.cats.informationmanagementservice.controller;

import com.cats.informationmanagementservice.service.SiblingDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info/siblingData")
@RequiredArgsConstructor
public class SiblingDataController {
    private final SiblingDataService siblingDataService;
}
