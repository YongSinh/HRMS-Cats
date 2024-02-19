package com.cats.informationmanagementservice.Dto;

import com.cats.informationmanagementservice.model.Department;
import com.cats.informationmanagementservice.model.Position;

import java.util.ArrayList;
import java.util.List;

public class mapper {

    public static PositionDtoRep PosToPositionResponseDto(Position position) {
        PositionDtoRep positionDtoRep = new PositionDtoRep();
        positionDtoRep.setId(position.getPosId());
        positionDtoRep.setPosName(position.getPosName());
        positionDtoRep.setDepName(position.getDepartment().getDepName());
        return positionDtoRep;
    }
    public static List<PositionDtoRep> PosToPositionResponseDtos(List<Position> positions) {
        List<PositionDtoRep> positionDtoReps = new ArrayList<>();
        for (Position position: positions) {
            positionDtoReps.add(PosToPositionResponseDto(position));
        }
        return positionDtoReps;
    }

    public static DepartmentDtoRep DepToDepartmentDtoRep(Department department){
        DepartmentDtoRep departmentDtoRep = new DepartmentDtoRep();
        departmentDtoRep.setId(department.getDepId());
        departmentDtoRep.setDepName(department.getDepName());
        return  departmentDtoRep;
    }

}
