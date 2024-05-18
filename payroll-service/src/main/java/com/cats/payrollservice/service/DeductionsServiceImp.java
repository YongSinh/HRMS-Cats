package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.DeductionsReqDto;
import com.cats.payrollservice.model.Allowances;
import com.cats.payrollservice.model.Deductions;
import com.cats.payrollservice.repository.DeductionsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeductionsServiceImp implements DeductionsService{
    private final DeductionsRepo deductionsRepo;
    @Override
    public Deductions create(DeductionsReqDto deductionsReqDto) {
        Deductions deductions = new Deductions();
        deductions.setDeduction(deductionsReqDto.getDeduction());
        deductions.setDescription(deductionsReqDto.getDescription());
        deductionsRepo.save(deductions);
        return deductions;
    }

    @Override
    public Deductions update(DeductionsReqDto deductionsReqDto, Long Id) {
        Deductions deductions = getDeductionsById(Id);
        deductions.setDeduction(deductionsReqDto.getDeduction());
        deductions.setDescription(deductionsReqDto.getDescription());
        deductionsRepo.save(deductions);
        return deductions;
    }

    @Override
    public Deductions getDeductionsById(Long Id) {
            return deductionsRepo.findById(Id).orElseThrow(() ->
                    new IllegalArgumentException(
                            "Deductions with id: " + Id + " could not be found"));


    }

    @Override
    public List<Deductions> getListDeduction() {
        return deductionsRepo.findAll();
    }

    @Override
    public void delete(Long Id) {
        Deductions deductions = getDeductionsById(Id);
        deductionsRepo.delete(deductions);
    }
}
