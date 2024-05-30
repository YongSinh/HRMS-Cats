package com.cats.payrollservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class ServiceCalculate {
    public Double roundUp(double value){
        String str = String.format("%1.2f", value);
        value = Double.parseDouble(str);
        return value;
    }
}
