package jv.bazar.amacame.factory.impl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;
import jv.bazar.amacame.dto.req.PromoReqDTO;
import jv.bazar.amacame.dto.res.PromoResDTO;
import jv.bazar.amacame.entity.Product;
import jv.bazar.amacame.entity.Promos;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.factory.IProductPromo;
import jv.bazar.amacame.repositories.ProductRepository;
import jv.bazar.amacame.repositories.PromoRepository;
import jv.bazar.amacame.services.BillService;
import jv.bazar.amacame.services.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static jv.bazar.amacame.cons.PromosConstans.DISCOUNT_PRODUCT_COMP;

@Component(DISCOUNT_PRODUCT_COMP)
public class ProductPercentPromo implements IProductPromo {
    @Autowired
    PromoRepository promoRepository;

    @Override
    public BigDecimal applyPromo(BigDecimal amountToDiscount, PromoResDTO promo, Long quantity) {
        BigDecimal priceAfterPromo;
        try {
            Promos promoToApplied = promoRepository.findByPromoIdAndPromoType(promo.getPromoId(), promo.getPromoType());
            if (promoToApplied == null) {
                throw new Exception("La promoción no existe");
            }
            priceAfterPromo = amountToDiscount.subtract(amountToDiscount.multiply(promoToApplied.getPromoValue()));
            return priceAfterPromo;
        } catch (Exception e) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Error al aplicar el descuento", e.getMessage());
        }
    }
}
