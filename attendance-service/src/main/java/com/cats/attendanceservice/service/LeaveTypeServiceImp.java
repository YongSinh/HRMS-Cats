package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.LeaveTypeReqDto;
import com.cats.attendanceservice.model.LeaveType;
import com.cats.attendanceservice.repository.LeaveTypeRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LeaveTypeServiceImp implements LeaveTypeService{
    private final LeaveTypeRepo leaveTypeRepo;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public LeaveType create(LeaveTypeReqDto leaveTypeReqDto) {
        LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(leaveTypeReqDto.getId());
        leaveType1.setLeaveDes(leaveTypeReqDto.getLeaveDes());
        leaveType1.setLeaveTitle(leaveTypeReqDto.getLeaveTitle());
        leaveType1.setLeaveDayPerYear(leaveTypeReqDto.getLeaveDayPerYear());
        return leaveTypeRepo.save(leaveType1);
    }

    @Override
    public LeaveType edit(LeaveTypeReqDto leaveTypeReqDto, String Id) {
        LeaveType leaveType1 = getLeave(Id);
        leaveType1.setLeaveDes(leaveTypeReqDto.getLeaveDes());
        leaveType1.setLeaveTitle(leaveTypeReqDto.getLeaveTitle());
        leaveType1.setLeaveDayPerYear(leaveTypeReqDto.getLeaveDayPerYear());
        return leaveTypeRepo.save(leaveType1);
    }

    @Override
    public LeaveType getLeave(String Id) {
        return leaveTypeRepo.findById(Id).orElseThrow(() ->
                new IllegalArgumentException(
                        "leave type with id: " + Id + " could not be found"));
    }

    @Override
    public List<LeaveType> getListLeave() {
        return leaveTypeRepo.findAll();
    }
    @Transactional
    @Override
    public void delete(String Id) {
        LeaveType leaveType = getLeave(Id);
        leaveTypeRepo.delete(leaveType);
    }
    /**
     * This will update leave balance in leave type tbl
     * and in leave balance tbl without reset the in leave balance tbl
     * */
    @Transactional
    @Override
    public void updateLeaveBalance(String leaveTypeId, Long newValue) {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("updateMultipleLeaveBalance2");

        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("leave_typeId", leaveTypeId)
                .addValue("new_value",  newValue);

        Map<String, Object> out = call.execute(in);
        System.out.println(out);
    }
}
