package jv.bazar.amacame.factory.impl;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;
import jv.bazar.amacame.factory.IProductPromo;
import jv.bazar.amacame.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static jv.bazar.amacame.cons.PromosConstans.DISCOUNT_PRODUCT_COMP;

@Component(DISCOUNT_PRODUCT_COMP)
public class ProductPercentPromo implements IProductPromo {

    @Autowired
    private BillService billService;
    @Override
    public BillDetailLineReqDTO applyPromo( BillDetailLineReqDTO billDetailLine) {
        try {
                if (billDetailLine.getPromo() != null) {
                    // promo value discount 0.1 - 1
                    BigDecimal promoValue = billDetailLine.getPromo().getPromoValue();
                    // price after promo
                    billDetailLine.setTotalPriceByProduct(
                            billDetailLine.getTotalPriceByProduct().subtract(
                                    billDetailLine.getTotalPriceByProduct().multiply(promoValue)
                            )
                    );
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billDetailLine;
    }
}
