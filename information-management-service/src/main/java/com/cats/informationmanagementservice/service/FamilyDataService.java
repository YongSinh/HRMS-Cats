package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.FamilyDataDtoReq;
import com.cats.informationmanagementservice.model.FamilyData;

import java.util.List;
import java.util.Optional;

public interface FamilyDataService {
    FamilyData create (FamilyDataDtoReq familyDataDtoReq);
    FamilyData edit (FamilyDataDtoReq familyDataDtoReq, Long Id);
    FamilyData getFamilyDataById (Long Id);
    List<FamilyData> getListFamilyData();
    Optional<FamilyData> getListFamilyDataByEmId(Long emId);
    void delete (Long Id);

}
