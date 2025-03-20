package jv.bazar.amacame.strategies;

import jv.bazar.amacame.dto.req.BillDetailLineReqDTO;
import jv.bazar.amacame.dto.req.BillReqDTO;
import jv.bazar.amacame.interfaces.ICalculationService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component()
public class CalculateProfitStrategy implements ICalculationService {

    @Override
    public BigDecimal calculate(BillReqDTO billReqDTO) {
        List<BillDetailLineReqDTO> billDetailLine = billReqDTO.getBillDetail().getBillDetailLines();
        BigDecimal totalProfit = BigDecimal.ZERO;
        for (BillDetailLineReqDTO billDetailLineReqDTO : billDetailLine) {
            totalProfit = totalProfit.add(billDetailLineReqDTO.getTotalProfitByProduct());
        }
        return totalProfit;
    }
}
