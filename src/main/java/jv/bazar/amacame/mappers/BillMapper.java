package jv.bazar.amacame.mappers;

import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.dto.res.BillResDTO;
import jv.bazar.amacame.entity.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BillMapper {
    BillMapper INSTANCE = Mappers.getMapper(BillMapper.class);

    Bill billReqDtoToBill(BillReqDTO billReqDto);

    BillResDTO billToBillResDTO(Bill bill);

    List<BillResDTO> billListToBillResDTOList(List<Bill> billList);
}
