package jv.bazar.amacame.factory.impl;

import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.factory.IPromo;
import org.springframework.stereotype.Component;

@Component
public class ProductScopePromo implements IPromo {
    @Override
    public BillReqDTO applyPromo(BillReqDTO bill) {

        System.out.println("Product scope promo applied");
        return null;

    }
}
