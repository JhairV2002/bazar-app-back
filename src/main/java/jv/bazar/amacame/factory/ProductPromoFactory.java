package jv.bazar.amacame.factory;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductPromoFactory {
    @Autowired
    private BeanFactory beanFactory;

    private IProductPromo productPromo;

    public List<BillDetailLineReqDTO> applyProductPromos(List<BillDetailLineReqDTO> billDetailLines) {
        List<BillDetailLineReqDTO> billDetailLinesPromo = new ArrayList<>();
        for (BillDetailLineReqDTO billDetailLine : billDetailLines) {
            if (billDetailLine.getPromo() != null) {
                productPromo = beanFactory.getBean(billDetailLine.getPromo().getPromoType(), IProductPromo.class);
                billDetailLine = productPromo.applyPromo(billDetailLine);
                billDetailLinesPromo.add(billDetailLine);
            }
        }
        return  billDetailLinesPromo;
    }
}
