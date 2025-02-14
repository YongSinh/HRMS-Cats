package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.JobHistoryDtoReq;
import com.cats.informationmanagementservice.model.JobHistory;

import java.util.List;

public interface JobHistoryService {
    JobHistory create(JobHistoryDtoReq jobHistoryDtoReq);

    JobHistory edit(JobHistoryDtoReq jobHistoryDtoReq, Long Id);

    JobHistory getById(Long Id);

    void delete(Long Id);

    List<JobHistory> getListJobHistory();

    List<JobHistory> getListJobHistoryBy(Long emId);

}
