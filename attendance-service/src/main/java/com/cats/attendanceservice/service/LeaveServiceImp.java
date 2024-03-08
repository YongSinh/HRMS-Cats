package com.cats.attendanceservice.service;

import com.cats.attendanceservice.repository.LeaveRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveServiceImp implements LeaveSerivce{
    private final LeaveRepo leaveRepo;
}
