package com.cats.informationmanagementservice.service;

import com.cats.informationmanagementservice.Dto.PositionDtoRep;
import com.cats.informationmanagementservice.Dto.PositionDtoReq;
import com.cats.informationmanagementservice.model.Position;

import java.util.List;

public interface PositionService {
    Position getPositionById (String Id);
    Position addPosition (PositionDtoReq positionDtoReq);
    List<PositionDtoRep> getListPosition();
    Position editPosition (PositionDtoReq positionDtoReq, String Id);
    PositionDtoRep deletePosition(String Id);
}
