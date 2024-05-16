package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.EducationDtoReq;
import com.cats.informationmanagementservice.Dto.EmergencyContactDtoReq;
import com.cats.informationmanagementservice.model.EmergencyContact;

import java.util.List;

public interface EmergencyService {

    EmergencyContact create(EmergencyContactDtoReq emergencyContactDtoReq);
    EmergencyContact edit(EmergencyContactDtoReq emergencyContactDtoReq, Long Id);
    List<EmergencyContact> getList();
    List<EmergencyContact> getListEmergencyContactByEmId(Long emId);
    EmergencyContact getEmergencyContactById( Long Id);
    void delete( Long Id);

}
