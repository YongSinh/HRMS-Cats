package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.EducationDtoReq;
import com.cats.informationmanagementservice.model.Education;

import java.util.List;

public interface EducationService {
    Education create(EducationDtoReq educationDtoReq);
    Education edit(EducationDtoReq educationDtoReq, Long Id);
    List<Education> getEducation();
    List<Education> getEducationByEmId(Long emId);
    Education getEducationById(Long Id);

    void delete(Long Id);

}
