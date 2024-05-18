package com.cats.payrollservice.service;

import com.cats.payrollservice.dto.request.AllowancesReqDto;
import com.cats.payrollservice.model.Allowances;
import com.cats.payrollservice.repository.AllowancesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllowancesServiceImp implements AllowancesService{

    private final AllowancesRepo allowancesRepo;

    @Override
    public Allowances create(AllowancesReqDto allowancesReqDto) {
        Allowances allowances = new Allowances();
        allowances.setAllowances(allowancesReqDto.getAllowances());
        allowances.setDescription(allowancesReqDto.getAllowances());
        allowancesRepo.save(allowances);
        return allowances;
    }

    @Override
    public Allowances update(AllowancesReqDto allowancesReqDto, Long Id) {
        Allowances allowances = getAllowancesBytId(Id);
        allowances.setAllowances(allowancesReqDto.getAllowances());
        allowances.setDescription(allowancesReqDto.getAllowances());
        allowancesRepo.save(allowances);
        return allowances;
    }

    @Override
    public void delete(Long id) {
        Allowances allowances = getAllowancesBytId(id);
        allowancesRepo.delete(allowances);
    }

    @Override
    public Allowances getAllowancesBytId(Long id) {
        return allowancesRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Allowances with id: " + id + " could not be found"));
    }

    @Override
    public List<Allowances> getListAllowances() {
        return allowancesRepo.findAll();
    }
}
