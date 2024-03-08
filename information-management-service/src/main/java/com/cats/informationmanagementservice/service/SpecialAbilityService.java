package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.SpecialAbilityDtoReq;
import com.cats.informationmanagementservice.model.SpecialAbility;

import java.util.List;

public interface SpecialAbilityService {
    SpecialAbility create (SpecialAbilityDtoReq specialAbilityDtoReq);
    SpecialAbility edit (SpecialAbilityDtoReq specialAbilityDtoReq, Long id);
    SpecialAbility getSpecialAbilityById (Long id);
    void delete(Long id);
    List<SpecialAbility> getListSpecialAbility();
}
