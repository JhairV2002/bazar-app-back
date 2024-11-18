package jv.bazar.amacame.factory.impl;

import jv.bazar.amacame.cons.PromosConstans;
import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.exceptions.CustomErrorException;
import jv.bazar.amacame.factory.IBillPromo;
import jv.bazar.amacame.repositories.PromoRepository;
import jv.bazar.amacame.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component(PromosConstans.DISCOUNT_BILL_COMP)
public class BillPercentPromo implements IBillPromo {
    @Autowired
    private PromoRepository promoRepository;

    @Autowired
    private BillService billService;
    @Override
    public BillReqDTO applyBillPromo(BillReqDTO bill) {
        try {
            BigDecimal promoValue;
            if (promoRepository.findById(bill.getPromo().getPromoId()).isEmpty()) {
                throw new CustomErrorException(HttpStatus.BAD_REQUEST, "El valor de la promoción es requerido", "El valor de la promoción es requerido");
            }

            promoValue = promoRepository.findById(bill.getPromo().getPromoId()).get().getPromoValue();

            bill.setBillTotal(
                    bill.getBillTotal().subtract(
                            bill.getBillTotal().multiply(promoValue)
                    )
            );

            bill.setBillProfit(
                    bill.getBillProfit().subtract(
                            bill.getBillProfit().multiply(promoValue)
                    )
            );
        }
        catch (Exception e) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Error al aplicar la promoción de factura", e.getMessage());
        }

        return bill;
    }
}
