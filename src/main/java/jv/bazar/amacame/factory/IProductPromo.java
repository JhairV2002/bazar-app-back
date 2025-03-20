package jv.bazar.amacame.factory;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;
import jv.bazar.amacame.dto.req.PromoReqDTO;
import jv.bazar.amacame.dto.res.PromoResDTO;

import java.math.BigDecimal;


public interface IProductPromo {
    BigDecimal applyPromo(BigDecimal amountToDiscount, PromoResDTO promo, Long quantity);
}
