package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.mapper;
import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto;
import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto2;
import com.cats.payrollservice.dto.request.EmployeeAllowancesReqDto3;
import com.cats.payrollservice.dto.response.EmployeeAllowancesRepDto;
import com.cats.payrollservice.model.Allowances;
import com.cats.payrollservice.model.EmployeeAllowances;
import com.cats.payrollservice.model.EmployeeDeductions;
import com.cats.payrollservice.model.Payslip;
import com.cats.payrollservice.repository.EmployeeAllowancesRepo;
import com.cats.payrollservice.repository.PayslipRepo;
import jakarta.ws.rs.ext.ParamConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableTransactionManagement
@Lazy
public class EmployeeAllowancesServiceImp implements EmployeeAllowancesService {
    private final EmployeeAllowancesRepo employeeAllowancesRepo;
    private final AllowancesService allowancesService;
    private final PayslipService payslipService;


    //This will add Allowances that same amount to all payslip
    @Override
    public List<EmployeeAllowancesRepDto> addAllowancesThatSame(EmployeeAllowancesReqDto3 employeeAllowancesReqDto) {
        if (employeeAllowancesReqDto.getAllowances() == null) {
            throw new IllegalArgumentException("Allowances list cannot be null or empty.");
        }
        List<EmployeeAllowances> employeeAllowancesArrayList = new ArrayList<>();
        // Get the date range from the request (assuming startDate and endDate are included)
        LocalDate startDate = employeeAllowancesReqDto.getStartDate();
        LocalDate endDate = employeeAllowancesReqDto.getEndDate();

        // Get the list of payslips within the date range
        List<Payslip> payslips = payslipService.getPayslipsByDateRange(startDate, endDate);

        if (payslips.isEmpty()) {
            throw new IllegalArgumentException("No payslips found for the given date range.");
        }

        // Retrieve the Allowances object
        Allowances allowances = allowancesService.getAllowancesBytId(employeeAllowancesReqDto.getAllowances());

        // Loop through each payslip and add the allowance
        for (Payslip payslip : payslips) {
            // Create a new EmployeeAllowances record for each payslip
            EmployeeAllowances employeeAllowances = new EmployeeAllowances();
            employeeAllowances.setEmpId(payslip.getEmpId());
            employeeAllowances.setType(employeeAllowancesReqDto.getType());
            employeeAllowances.setAmount(employeeAllowancesReqDto.getAmount());
            employeeAllowances.setEffectiveDate(employeeAllowancesReqDto.getEffectiveDate());
            employeeAllowances.setDateCreated(employeeAllowancesReqDto.getDateCreated());
            employeeAllowances.setAllowances(allowances);
            employeeAllowances.setPaySlipId(payslip.getId());
            // Add the allowance to the current payslip
            payslipService.addAllowanceToPaySlipMore2(payslip.getId(), employeeAllowancesReqDto.getAmount(), allowances.getAllowances());
            employeeAllowancesArrayList.add(employeeAllowances);
            // Save the employee allowances record
        }
        employeeAllowancesRepo.saveAll(employeeAllowancesArrayList);
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDtos(employeeAllowancesArrayList);
    }

    @Override
    public List<EmployeeAllowancesRepDto> addMoreToPaySlip(EmployeeAllowancesReqDto employeeAllowancesReqDto, Long emId, Long id) {
        List<EmployeeAllowances> employeeAllowancesArrayList = new ArrayList<>();
        double totalAmount = 0.0;
        if (employeeAllowancesReqDto.getAllowances() == null || employeeAllowancesReqDto.getAllowances().isEmpty()) {
            throw new IllegalArgumentException("Allowances list cannot be null or empty.");
        }
            List<String> allowanceList = new ArrayList<>();
            Payslip payslip = payslipService.getListPaySlipByeEmIdAndCreateDate(emId,employeeAllowancesReqDto.getPaySlipDate());
            for (Long allowance:employeeAllowancesReqDto.getAllowances()){
                EmployeeAllowances employeeAllowances = new EmployeeAllowances();
                employeeAllowances.setEmpId(emId);
                employeeAllowances.setType(employeeAllowancesReqDto.getType());
                employeeAllowances.setAmount(employeeAllowancesReqDto.getAmount());
                employeeAllowances.setEffectiveDate(employeeAllowancesReqDto.getEffectiveDate());
                employeeAllowances.setDateCreated(employeeAllowancesReqDto.getDateCreated());
                Allowances allowances = allowancesService.getAllowancesBytId(allowance);
                allowanceList.add(allowances.getAllowances());
                employeeAllowances.setAllowances(allowances);
                employeeAllowancesArrayList.add(employeeAllowances);
                employeeAllowances.setPaySlipId(payslip.getId());
            }
            totalAmount += employeeAllowancesReqDto.getAmount();
            payslipService.addAllowanceToPaySlipMore(id,totalAmount,allowanceList);
            employeeAllowancesRepo.saveAll(employeeAllowancesArrayList);
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDtos(employeeAllowancesArrayList);
    }

    @Transactional
    @Override
    public List<EmployeeAllowancesRepDto> createMultiple(EmployeeAllowancesReqDto employeeAllowancesReqDto, List<Long> emId) {
        List<EmployeeAllowances> employeeAllowancesArrayList = new ArrayList<>();
        double totalAmount = 0.0;
        if (employeeAllowancesReqDto.getAllowances() == null || employeeAllowancesReqDto.getAllowances().isEmpty()) {
            throw new IllegalArgumentException("Allowances list cannot be null or empty.");
        }
        for (Long emIds : emId){
            List<String> allowanceList = new ArrayList<>();
            Payslip payslip = payslipService.getListPaySlipByeEmIdAndCreateDate(emIds,employeeAllowancesReqDto.getPaySlipDate());
            for (Long allowance:employeeAllowancesReqDto.getAllowances()){
                EmployeeAllowances employeeAllowances = new EmployeeAllowances();
                employeeAllowances.setEmpId(emIds);
                employeeAllowances.setType(employeeAllowancesReqDto.getType());
                employeeAllowances.setAmount(employeeAllowancesReqDto.getAmount());
                employeeAllowances.setEffectiveDate(employeeAllowancesReqDto.getEffectiveDate());
                employeeAllowances.setDateCreated(employeeAllowancesReqDto.getDateCreated());
                Allowances allowances = allowancesService.getAllowancesBytId(allowance);
                allowanceList.add(allowances.getAllowances());
                employeeAllowances.setAllowances(allowances);
                employeeAllowancesArrayList.add(employeeAllowances);
                employeeAllowances.setPaySlipId(payslip.getId());
            }
            totalAmount += employeeAllowancesReqDto.getAmount();
            payslipService.addAllowanceToPaySlip(emIds,employeeAllowancesReqDto.getPaySlipDate(), totalAmount, allowanceList);
        }

        employeeAllowancesRepo.saveAll(employeeAllowancesArrayList);
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDtos(employeeAllowancesArrayList);
    }

    @Override
    public EmployeeAllowancesRepDto addAllowances(EmployeeAllowancesReqDto2 employeeAllowancesReqDto) {
        if (employeeAllowancesReqDto.getAllowances() == null ) {
            throw new IllegalArgumentException("Allowances list cannot be null or empty.");
        }

        Payslip payslip = payslipService.getPaySlipById(employeeAllowancesReqDto.getPaySlipId());
        EmployeeAllowances employeeAllowances = new EmployeeAllowances();
        employeeAllowances.setEmpId(payslip.getEmpId());
        employeeAllowances.setType(employeeAllowancesReqDto.getType());
        employeeAllowances.setAmount(employeeAllowancesReqDto.getAmount());
        employeeAllowances.setEffectiveDate(employeeAllowancesReqDto.getEffectiveDate());
        employeeAllowances.setDateCreated(employeeAllowancesReqDto.getDateCreated());
        Allowances allowances = allowancesService.getAllowancesBytId(employeeAllowancesReqDto.getAllowances());
        employeeAllowances.setAllowances(allowances);
        employeeAllowances.setPaySlipId(payslip.getId());
        payslipService.addAllowanceToPaySlipMore2(payslip.getId(), employeeAllowancesReqDto.getAmount(), allowances.getAllowances());
        employeeAllowancesRepo.save(employeeAllowances);
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDto(employeeAllowances);
    }

    @Override
    public List<EmployeeAllowances> getAllowancesForCurrentMonth(Long emId) {
        YearMonth currentMonth = YearMonth.now(); // Get current month
        LocalDate startOfMonth = currentMonth.atDay(1); // First day of the month
        LocalDate endOfMonth = currentMonth.atEndOfMonth(); // Last day of the month
        return employeeAllowancesRepo.findByEffectiveDateForCurrentMonth(startOfMonth,endOfMonth,emId);
    }

    @Override
    public void delete(Long id) {
        EmployeeAllowances employeeAllowances = getEmpAllowancesById(id);
        String allowance = employeeAllowances.getAllowances().getAllowances();
        double allowanceAmount = employeeAllowances.getAmount();
        payslipService.removeAllowanceFromPaySlip(employeeAllowances.getPaySlipId(), allowance,allowanceAmount);
        employeeAllowancesRepo.delete(employeeAllowances);
    }

    @Override
    public void deleteEmpAllowanceByPaySlipId(Long id) {
        try {
            List<EmployeeAllowances> delete = employeeAllowancesRepo.findByPaySlipId(id);

            if (delete != null && !delete.isEmpty()) {
                employeeAllowancesRepo.deleteAll(delete);
            } else {
                throw  new IllegalArgumentException("No employee allowance found for pay slip ID: " + id);
            }
        } catch (Exception e) {
            throw e;  // Rethrow the exception if you want the caller to handle it
        }

    }

    @Transactional
    @Override
    public EmployeeAllowancesRepDto update(EmployeeAllowancesReqDto2 employeeAllowancesReqDto, Long Id) {
        EmployeeAllowances update = getEmpAllowancesById(Id);
        double oldAmount = update.getAmount();
        double newAmount = employeeAllowancesReqDto.getAmount();
        update.setType(employeeAllowancesReqDto.getType());
        update.setAmount(employeeAllowancesReqDto.getAmount());
        update.setEffectiveDate(employeeAllowancesReqDto.getEffectiveDate());
        update.setDateCreated(employeeAllowancesReqDto.getDateCreated());
        String oldAllowance = update.getAllowances().getAllowances();
        String allowance = null;
        if(employeeAllowancesReqDto.getAllowances() != null){
                Allowances allowances = allowancesService.getAllowancesBytId(employeeAllowancesReqDto.getAllowances() );
                //allowanceList.add(allowances.getAllowances());
                allowance = allowances.getAllowances();
                update.setAllowances(allowances);
        }
        employeeAllowancesRepo.save(update);
        payslipService.updateAllowanceToPaySlip2(update.getPaySlipId(),newAmount, oldAmount, allowance, oldAllowance);
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDto(update);
    }

    @Override
    public EmployeeAllowances getEmpAllowancesById(Long id) {
        return employeeAllowancesRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Employee Allowances with id: " + id + " could not be found"));
    }

    @Override
    public EmployeeAllowancesRepDto getEmpAllowances(Long id) {
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDto(getEmpAllowancesById(id));
    }

    @Override
    public List<EmployeeAllowancesRepDto> getListEmpAllowances() {

        return mapper.employeeAllowancesToEmployeeAllowancesResponseDtos(employeeAllowancesRepo.findAllByOrderByEmpIdDesc());
    }

    @Override
    public List<EmployeeAllowancesRepDto> getListEmpAllowancesByPaySlip(Long id) {
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDtos(employeeAllowancesRepo.findByPaySlipId(id));
    }

    @Override
    public List<EmployeeAllowancesRepDto> getListEmpAllowancesByEmId(Long emId) {
        return mapper.employeeAllowancesToEmployeeAllowancesResponseDtos(employeeAllowancesRepo.findByEmpId(emId));
    }
}
