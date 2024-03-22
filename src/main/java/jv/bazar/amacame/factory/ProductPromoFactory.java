package jv.bazar.amacame.factory;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductPromoFactory {
    private final BeanFactory beanFactory;

    public ProductPromoFactory(BeanFactory beanFactory) {
       this.beanFactory = beanFactory;
    }

    public  IProductPromo getPromo(String promoType) {

            return beanFactory.getBean(promoType, IProductPromo.class);

    }
}
