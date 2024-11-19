package jv.bazar.amacame.services;

import jv.bazar.amacame.dto.req.PromoReqDTO;
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

    public ResponseEntity<List<PromoResDTO>> getAllPromos() {
        return new ResponseEntity<>(promosMapper.promosListToPromoResDTOList(promoRepository.findByisActive(true)), HttpStatus.OK);
    }

    public ResponseEntity<PromoResDTO> updatePromo(PromoReqDTO promoReqDTO) {
        return new ResponseEntity<>(
                promosMapper.promosToPromoResDTO(
                promoRepository.save(promosMapper.promoReqDTOToPromos(promoReqDTO))),
                HttpStatus.OK
        );
    }

    public ResponseEntity<PromoResDTO> createPromo(PromoReqDTO promoReqDTO) {
        return new ResponseEntity<>(
                promosMapper.promosToPromoResDTO(
                promoRepository.save(promosMapper.promoReqDTOToPromos(promoReqDTO))),
                HttpStatus.CREATED
        );
    }
}
