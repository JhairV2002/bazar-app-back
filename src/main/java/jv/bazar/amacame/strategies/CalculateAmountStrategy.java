package jv.bazar.amacame.strategies;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;
import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.interfaces.ICalculationService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static jv.bazar.amacame.cons.CalculateComponentsNames.CALCULATE_AMOUNT_STRATEGY;

@Component(CALCULATE_AMOUNT_STRATEGY)
public class CalculateAmountStrategy implements ICalculationService {

    @Override
    public BigDecimal calculate(BillReqDTO billReqDTO) {
        List<BillDetailLineReqDTO> billDetailLine = billReqDTO.getBillDetail().getBillDetailLines();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (BillDetailLineReqDTO billDetailLineReqDTO : billDetailLine) {
            totalPrice = totalPrice.add(billDetailLineReqDTO.getTotalPriceByProduct());
        }
        return totalPrice;
    }
}
