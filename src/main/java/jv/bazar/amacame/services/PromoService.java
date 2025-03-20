package jv.bazar.amacame.services;

import jv.bazar.amacame.dto.req.PromoReqDTO;
import jv.bazar.amacame.dto.res.GenericResponseDTO;
import jv.bazar.amacame.dto.res.PromoResDTO;
import jv.bazar.amacame.entity.Promos;
import jv.bazar.amacame.enums.PromoTypeEnum;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.mappers.PromosMapper;
import jv.bazar.amacame.repositories.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PromoService {
    @Autowired
    private PromoRepository promoRepository;

    @Autowired
    private PromosMapper promosMapper;

    public GenericResponseDTO<List<PromoResDTO>> getAllPromos() {
        return GenericResponseDTO.<List<PromoResDTO>>builder()
                .status(HttpStatus.OK)
                .message("Transaccion exitosa")
                .code(HttpStatus.OK.value())
                .data(promosMapper.promosListToPromoResDTOList(promoRepository.findByisActive(true)))
                .build();
    }

    public GenericResponseDTO<PromoResDTO> updatePromo(PromoReqDTO promoReqDTO) {

        return GenericResponseDTO.<PromoResDTO>builder()
                .status(HttpStatus.OK)
                .message("Transaccion exitosa")
                .code(HttpStatus.OK.value())
                .data(promosMapper.promosToPromoResDTO(
                        promoRepository.save(promosMapper.promoReqDTOToPromos(promoReqDTO))))
                .build();
    }

    public GenericResponseDTO<PromoResDTO> createPromo(PromoReqDTO promoReqDTO) {
        return GenericResponseDTO.<PromoResDTO>builder()
                .status(HttpStatus.CREATED)
                .message("Transaccion exitosa")
                .code(HttpStatus.CREATED.value())
                .data(promosMapper.promosToPromoResDTO(
                        promoRepository.save(promosMapper.promoReqDTOToPromos(promoReqDTO))))
                .build();
    }
}
