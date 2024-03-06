package jv.bazar.amacame.factory.impl;

import jv.bazar.amacame.cons.PromosConstans;
import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;
import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.factory.IProductPromo;
import jv.bazar.amacame.factory.IPromo;
import jv.bazar.amacame.services.BillDetailervice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

@Component(PromosConstans.SPECIAL_PROMOS_COMP)
public class ProductSpecialPromo implements IProductPromo {
    @Override
    public BillDetailLineReqDTO applyPromo( BillDetailLineReqDTO billDetailLine) {
            if (billDetailLine.getPromo() != null) {
                billDetailLine.setQuantity(billDetailLine.getPromo().getSpecialPromoX());
                billDetailLine.setTotalPriceByProduct(billDetailLine.getProduct().getProductSalePrice().multiply(
                     new BigDecimal( billDetailLine.getPromo().getSpecialPromoY())
                ));
                billDetailLine.setTotalProfitByProduct(billDetailLine.getProduct().getProductSalePrice().subtract(
                        billDetailLine.getProduct().getProductPurchasePrice()
                ).multiply(new BigDecimal(billDetailLine.getPromo().getSpecialPromoX())));
            }
        return billDetailLine;
    }
}
