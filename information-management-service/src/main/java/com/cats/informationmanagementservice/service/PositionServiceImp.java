package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.PositionDtoRep;
import com.cats.informationmanagementservice.Dto.PositionDtoReq;
import com.cats.informationmanagementservice.Dto.mapper;
import com.cats.informationmanagementservice.model.Department;
import com.cats.informationmanagementservice.model.Position;
import com.cats.informationmanagementservice.repository.PositionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImp implements PositionService {
    private final PositionRepo positionRepo;
    private final DepartmentService departmentService;

    @Override
    public Position getPositionById(String Id) {
        return positionRepo.findById(Id).orElseThrow(() ->
                new IllegalArgumentException(
                        "Position with id: " + Id + " could not be found"));
    }

    @Override
    public Position addPosition(PositionDtoReq positionDtoReq) {
        Position position = new Position();
        position.setPosId(positionDtoReq.getPoId());
        position.setPosName(positionDtoReq.getPosName());
        position.setPoSection(positionDtoReq.getPoSection());
        position.setPoLevel(positionDtoReq.getPoLevel());
        if (positionDtoReq.getDepId() == null) {
            throw new IllegalArgumentException("Position at least on Department ");
        }
        Department department = departmentService.getDepById(positionDtoReq.getDepId());
        position.setDepartment(department);
        return positionRepo.save(position);
    }

    @Override
    public List<PositionDtoRep> getListPosition() {

        return mapper.PosToPositionResponseDtos(positionRepo.findAll());
    }

    @Override
    public List<PositionDtoRep> getLisPositionByDepId(Long depId) {
        return mapper.PosToPositionResponseDtos(positionRepo.findAllByDepartment_DepId(depId));
    }

    @Override
    public Position editPosition(PositionDtoReq positionDtoReq, String Id) {
        Position editPosition = getPositionById(Id);
        editPosition.setPosName(positionDtoReq.getPosName());
        editPosition.setPosName(positionDtoReq.getPosName());
        editPosition.setPoSection(positionDtoReq.getPoSection());
        editPosition.setPoLevel(positionDtoReq.getPoLevel());
        if (positionDtoReq.getDepId() != null) {
            Department department = departmentService.getDepById(positionDtoReq.getDepId());
            editPosition.setDepartment(department);
        }
        return positionRepo.save(editPosition);
    }

    @Override
    public PositionDtoRep deletePosition(String Id) {
        Position editPosition = getPositionById(Id);
        positionRepo.delete(editPosition);
        return mapper.PosToPositionResponseDto(editPosition);
    }
}
