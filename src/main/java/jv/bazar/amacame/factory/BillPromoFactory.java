package jv.bazar.amacame.factory;

import jv.bazar.amacame.dto.req.BillReqDTO;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class BillPromoFactory {
    @Autowired
    private BeanFactory beanFactory;

    private IBillPromo promo;

    public BillReqDTO applyBillPromo(String promoType, BillReqDTO bill) {
        promo = beanFactory.getBean(promoType, IBillPromo.class);
        return promo.applyBillPromo(bill);
    }
}
