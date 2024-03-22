package jv.bazar.amacame.factory.impl;

import jv.bazar.amacame.cons.PromosConstans;
import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;
import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.dto.res.PromoResDTO;
import jv.bazar.amacame.entity.Promos;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.factory.IProductPromo;
import jv.bazar.amacame.factory.IPromo;
import jv.bazar.amacame.repositories.PromoRepository;
import jv.bazar.amacame.services.BillDetailervice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

@Component(PromosConstans.SPECIAL_PROMOS_COMP)
public class ProductSpecialPromo implements IProductPromo {
    @Autowired
    private PromoRepository promoRepository;
    @Override
    public BigDecimal applyPromo( BigDecimal amountToDiscount, PromoResDTO promo, Long quantity) {
        BigDecimal priceAfterPromo;
        try {
            Promos promoToApplied = promoRepository.findByPromoIdAndPromoType(promo.getPromoId(), promo.getPromoType());
            if (promoToApplied == null
                    || promoToApplied.getSpecialPromoX() == null
                    || promoToApplied.getSpecialPromoY() == null) {
                throw new Exception("La promoción no existe o no tiene valores asignados");
            }
            Long promoX = promoToApplied.getSpecialPromoX();

            if ((quantity % promoX) != 0) {
                throw new Exception("La cantidad de productos no es válida para la promoción");
            }

            priceAfterPromo = amountToDiscount.subtract(amountToDiscount.multiply(promoToApplied.getPromoValue()));
            return priceAfterPromo;
        } catch (Exception e) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Error al aplicar el descuento", e.getMessage());
        }
    }
}
