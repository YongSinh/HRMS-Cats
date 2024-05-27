package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.WebFluxResponse;
import com.cats.payrollservice.dto.request.PayrollReqDto;
import com.cats.payrollservice.dto.request.PayslipReqDto;
import com.cats.payrollservice.dto.response.SalariesRepDto;
import com.cats.payrollservice.model.Payroll;
import com.cats.payrollservice.model.Salaries;
import com.cats.payrollservice.model.Tax;
import com.cats.payrollservice.repository.PayrollRepo;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PayrollServiceImp implements PayrollService {

    private final PayrollRepo payrollRepo;
    private final TaxService taxService;
    private final DeductionsService deductionsService;
    private final AllowancesService allowancesService;
    private final SalariesService salariesService;
    private final WebClient.Builder webClientBuilder;

    @Override
    public Payroll getPayrollById(Long id) {
        return payrollRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Payroll with id: " + id + " could not be found"));
    }

    @Override
    public List<Payroll> update(Long id, PayrollReqDto payrollReqDto, List<Long> emIds) {
        List<Payroll> payrollList = new ArrayList<>();
        for (Long emId : emIds){
            Payroll payroll = getPayrollById(id);
            String payrollReference = generatePayrollReference();
            payroll.setEmpId(emId);
            payroll.setRefNo(payrollReference);
            payroll.setDateFrom(payrollReqDto.getDateFrom());
            payroll.setDateTo(payrollReqDto.getDateTo());
            payroll.setDateCreate(payrollReqDto.getDateCreate());
            payroll.setType(payrollReqDto.getType());
            payroll.setStatus(payrollReqDto.getStatus());
        }
        payrollRepo.saveAll(payrollList);
        return payrollList;
    }


    @Override
    public List<Payroll> create(PayrollReqDto payrollReqDto, List<Long> emIds) {
        List<Payroll> payrollList = new ArrayList<>();
        for (Long emId :emIds){
            Payroll payroll = new Payroll();
            String payrollReference = generatePayrollReference();
            payroll.setEmpId(emId);
            payroll.setRefNo(payrollReference);
            payroll.setDateFrom(payrollReqDto.getDateFrom());
            payroll.setDateTo(payrollReqDto.getDateTo());
            payroll.setDateCreate(payrollReqDto.getDateCreate());
            payroll.setType(payrollReqDto.getType());
            payroll.setStatus(payrollReqDto.getStatus());
            payrollList.add(payroll);
        }
        payrollRepo.saveAll(payrollList);
        return payrollList;
    }


    @Override
    public String generatePayrollReference() {
        // Format current date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String datePart = sdf.format(new Date());

        // Generate a random number
        Random random = new Random();
        int randomPart = random.nextInt(1000000); // 6-digit random number

        // Combine date and random parts
        return datePart + "-" + String.format("%06d", randomPart);



    }

    @Override
    public List<Payroll> getListPayroll() {
        return payrollRepo.findAll();
    }

    @Override
    public List<Payroll> getListPayRollByEmId(Long emId) {
        return payrollRepo.findPayrollByEmpId(emId);
    }

    @Override
    public Payroll getListPayRollByEmIdAndCreateDate(Long emId, LocalDate date) {
        return payrollRepo.findPayrollByEmpIdAndDateCreate(emId, date);
    }

    @Override
    public Double calculateNetSalary(Long emId, Double KhRate) {
        Salaries salaries = salariesService.getSalary(emId);
        Double khMoney = salaries.getSalary() * KhRate;
        Double tax = taxService.taxCalculator(khMoney);
        return (khMoney - tax) / KhRate;
    }


    @Override
    public List<Payroll> findPayRollByDepEmId(Long depId) {
        WebFluxResponse response = webClientBuilder.build().get()
                .uri("http://information-management-service/api/info/employee/listEmployeeByDepOnlyEmId",
                        uriBuilder -> uriBuilder.queryParam("depId", depId).build())
                .retrieve()
                    .bodyToMono(WebFluxResponse.class)
                .block();
        if (response != null && response.getCode() == 200) {
            JsonNode emIdList = response.getData();
            List<Long> idList = new ArrayList<>();
            if (emIdList.isArray()) {
                for (JsonNode node : emIdList) {
                    idList.add(node.asLong());
                }
            }
            System.out.println(idList);
            return payrollRepo.findByEmpIdInOrderByDateCreate(idList);
        } else {
            System.out.println("No employee IDs found or response status is not OK.");
            return List.of();
        }

    }

    @Override
    public void deletePayroll(Long id) {
        Payroll delete = getPayrollById(id);
        payrollRepo.delete(delete);
    }
}
