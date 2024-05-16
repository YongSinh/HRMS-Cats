package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.SiblingDataDtoReq;
import com.cats.informationmanagementservice.model.SiblingData;

import java.util.List;

public interface SiblingDataService {
 List<SiblingData> getListSiblingData();
 List<SiblingData> getListSiblingDataByEmId(Long emId);
 SiblingData getSiblingData(Long id);
 SiblingData create(SiblingDataDtoReq siblingDataDtoReq);
 SiblingData edit(SiblingDataDtoReq siblingDataDtoReq, Long id);
 void delete( Long id);



}
