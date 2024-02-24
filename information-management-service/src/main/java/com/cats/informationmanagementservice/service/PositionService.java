package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.PositionDtoRep;
import com.cats.informationmanagementservice.Dto.PositionDtoReq;
import com.cats.informationmanagementservice.model.Position;

import java.util.List;

public interface PositionService {
    Position getPositionById (Long Id);
    Position addPosition (PositionDtoReq positionDtoReq);
    List<Position> getListPosition();
    Position editPosition (PositionDtoReq positionDtoReq, Long Id);

    PositionDtoRep deletePosition(Long Id);
}
