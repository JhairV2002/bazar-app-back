package jv.bazar.amacame.mappers;

import jv.bazar.amacame.dto.req.SpecialPromoReqDTO;
import jv.bazar.amacame.dto.res.PromoResDTO;
import jv.bazar.amacame.entity.Promos;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PromosMapper {
    PromosMapper INSTANCE = Mappers.getMapper(PromosMapper.class);

    List<PromoResDTO> promosListToPromoResDTOList(List<Promos> promoList);

    PromoResDTO promosToPromoResDTO(Promos promos);

    Promos promosReqDtoToPromos(SpecialPromoReqDTO promoReqDto);

    SpecialPromoReqDTO promosToPromoReqDTO(Promos promos);

    List<SpecialPromoReqDTO> promosListToPromoReqDTOList(List<Promos> promosList);
}
