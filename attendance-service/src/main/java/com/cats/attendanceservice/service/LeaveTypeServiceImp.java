package com.cats.attendanceservice.service;

import com.cats.attendanceservice.repository.LeaveTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaveTypeServiceImp implements LeaveTypeService{
    private final LeaveTypeRepo leaveTypeRepo;
}
