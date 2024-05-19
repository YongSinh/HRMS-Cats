package com.cats.payrollservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@SpringBootTest
class PayrollServiceApplicationTests {
    public static String generatePayrollReference() {
        // Format current date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String datePart = sdf.format(new Date());

        // Generate a random number
        Random random = new Random();
        int randomPart = random.nextInt(1000000); // 6-digit random number

        // Combine date and random parts
        return datePart + "-" + String.format("%06d", randomPart);
    }
    @Test
    void contextLoads() {
        String payrollReference = generatePayrollReference();
        System.out.println("Payroll Reference Number: " + payrollReference);
    }

}
